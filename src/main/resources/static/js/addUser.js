$(document).ready(function () {
    $('#addUserButton').click(function () {
        ajaxPost();

    });

    function ajaxPost() {
        var roleValue = "";
        var role = document.getElementById("authorityAddUser");
        if (role[role.selectedIndex].value === 2) {
            roleValue = "ROLE_ADMIN";
        } else {
            roleValue = "ROLE_USER";
        }
        // PREPARE FORM DATA
        var formData = {
            name: $("#nameAddUser").val(),
            login: $("#loginAddUser").val(),
            password: $("#passwordAddUser").val(),
            roles: [{
                id: role[role.selectedIndex].value,
                role: roleValue,
                authority: roleValue
            }
            ]
        };

        // DO POST
        $.ajax({
            type: "POST",
            contentType: "application/json;",
            url: "/api/user",
            data: JSON.stringify(formData),
            dataType: 'json',
            complete: [
                function () {
                    getAllUsers();
                    $(document).ready(function () {
                        $("#tableUsersTab").tab('show');
                        reset();
                    });
                    function reset(){
                        $('#nameAddUser').val('');
                        $('#loginAddUser').val('');
                        $('#passwordAddUser').val('');
                    }
                }
            ]
        });
    }
});