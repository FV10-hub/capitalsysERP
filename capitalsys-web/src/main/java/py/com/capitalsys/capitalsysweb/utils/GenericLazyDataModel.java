/**
 * 
 */
package py.com.capitalsys.capitalsysweb.utils;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;

import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.util.LocaleUtils;

/**
 * esta clase se va encargar de manejar la carga perezosa de los datatables en
 * los html
 */
public class GenericLazyDataModel<T> extends LazyDataModel<T> {

    private static final long serialVersionUID = 1L;

    private List<T> datasource;
    private Field field;

    public GenericLazyDataModel(List<T> datasource) {
        this.datasource = datasource;
    }

    @Override
	public T getRowData(String rowKey) {
		for(T o : datasource) {
            if(getFieldData(o, "id").equals(rowKey))
                return o;
        }
        return null;
	}

	@Override
	public String getRowKey(T entity) {
		// Assuming the entity has a unique identifier
		return String.valueOf(entity.hashCode());
	}

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) datasource.stream()
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .count();
    }

    @Override
    public List<T> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        // Aplica el desplazamiento y los filtros
        List<T> data = datasource.stream()
                .skip(offset)
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .limit(pageSize)
                .collect(Collectors.toList());

        // Ordena
        if (!sortBy.isEmpty()) {
            List<Comparator<T>> comparators = sortBy.values().stream()
                    .map(o -> new LazySorter<T>(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<T> cp = ComparatorUtils.chainedComparator(comparators); // desde apache
            data.sort(cp);
        }

        return data;
    }

    private boolean filter(FacesContext context, Collection<FilterMeta> filterBy, Object o) {
        boolean matching = true;

        for (FilterMeta filter : filterBy) {
            FilterConstraint constraint = filter.getConstraint();
            Object filterValue = filter.getFilterValue();

            try {
                Object columnValue = String.valueOf(CommonUtils.getPropertyValueViaReflection(o, filter.getField()));
                matching = constraint.isMatching(context, columnValue, filterValue, LocaleUtils.getCurrentLocale());
            }
            catch (ReflectiveOperationException | IntrospectionException e) {
                matching = false;
            }

            if (!matching) {
                break;
            }
        }

        return matching;
    }
    
    public String getFieldData(Object o, String key) {
		String g = null;
		try {
			if (key.contains(".")) {
				for (String s : key.split("\\.")) {
					field = o.getClass().getDeclaredField(s);
					field.setAccessible(true);
					o = field.get(o);
				}
				g = String.valueOf(o);
			} else {
				field = o.getClass().getDeclaredField(key);
				field.setAccessible(true);
				g = String.valueOf(field.get(o));
			}
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace(System.out);
		}
		return g;
	}
}