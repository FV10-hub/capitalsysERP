document.addEventListener("DOMContentLoaded", function() {
    // Declara variables
    var sideMenu = document.querySelector(".menu__side");
    var btnOpen = document.querySelector(".btn_open");
    var body = document.querySelector(".cuerpo");

    // Agrega un evento click al botón
    btnOpen.addEventListener("click", function() {
        toggleMenu();
    });

    // Función para mostrar u ocultar el menú
    function toggleMenu() {
        body.classList.toggle("body_move");
        sideMenu.classList.toggle("menu__side_move");
    }

    // Función para manejar la responsividad del menú
    function handleResponsiveMenu() {
        if (window.innerWidth < 760) {
            body.classList.add("body_move");
            sideMenu.classList.add("menu__side_move");
        } else {
            body.classList.remove("body_move");
            sideMenu.classList.remove("menu__side_move");
        }
    }

    // Ejecuta la función de manejo de responsividad al cargar y redimensionar la página
    window.addEventListener("load", handleResponsiveMenu);
    window.addEventListener("resize", handleResponsiveMenu);
});
