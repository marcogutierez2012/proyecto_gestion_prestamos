$(document).ready(function () {
    $("#selectPrestamista").change(function () {
        var selectPrestamista = $(this).val();
        if (selectPrestamista === '-1') {
            // Si se selecciona la opción "Seleccione", limpiar los campos del formulario
            $("#idZona").val('');
            $("#idUsuarioLider").val('');
            return;
        }
        console.log(selectPrestamista);
        console.log("Función AJAX ejecutada");
        $.ajax({
            url: "/buscarUsuario?selectPrestamista=" + selectPrestamista,
            type: "GET",
            success: function (usuario) {
                // Actualizar los campos del formulario con los datos del usuario
                $("#idZona").val(usuario.idZona.idZona);
                $("#idUsuarioLider").val(usuario.idUsuario);
                // Actualizar otros campos del formulario según sea necesario
                console.log(usuario); // <-- Aquí deberías imprimir usuario en lugar de url
            },
            error: function () {
                alert("Error al cargar los datos del usuario.");
            }
        });
    });
});
