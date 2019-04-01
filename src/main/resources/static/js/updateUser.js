$(document).ready(function () {
    $('#editUser').click(function () {
        ajaxPut();
    });

    function ajaxPut() {
        var roleValue = "";
        var role = document.getElementById("modalAuthority");
        if (role[role.selectedIndex].value === 2) {
            roleValue = "ROLE_ADMIN";
        } else {
            roleValue = "ROLE_USER";
        }
        // PREPARE FORM DATA
        var formData = {
            id :  $("#modalId").val(),
            name: $("#modalName").val(),
            login: $("#modalLogin").val(),
            password: $("#modalPassword").val(),
            roles: [{
                id: role[role.selectedIndex].value,
                role: roleValue,
                authority: roleValue
            }
            ]
        };

        // DO POST
        $.ajax({
            type: "PUT",
            contentType: "application/json;",
            url: "/api/users/" + $("#modalId").val(),
            data: JSON.stringify(formData),
            dataType: 'json',
            complete: [
                function () {
                    getAllUsers();
                    $('#exampleModal').modal('hide');
                }
            ]
        });
    }
});