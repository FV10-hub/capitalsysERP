document.addEventListener("DOMContentLoaded", function() {
    var usernameInput = document.querySelector(".username");
    var passwordInput = document.querySelector(".password");
    var userIcon = document.querySelector(".user-icon");
    var passIcon = document.querySelector(".pass-icon");

    usernameInput.addEventListener("focus", function() {
        userIcon.style.left = "-48px";
    });

    usernameInput.addEventListener("blur", function() {
        userIcon.style.left = "0px";
    });

    passwordInput.addEventListener("focus", function() {
        passIcon.style.left = "-48px";
    });

    passwordInput.addEventListener("blur", function() {
        passIcon.style.left = "0px";
    });
});
