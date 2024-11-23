 // Función para mostrar u ocultar la contraseña
 function togglePassword() {
    const passwordField = document.getElementById('password');
    const eyeIcon = document.getElementById('togglePassword');

    // Cambiar el tipo de input entre 'password' y 'text'
    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        eyeIcon.classList.remove('fa-eye-slash');
        eyeIcon.classList.add('fa-eye');
    } else {
        passwordField.type = 'password';
        eyeIcon.classList.remove('fa-eye');
        eyeIcon.classList.add('fa-eye-slash');
    }
}