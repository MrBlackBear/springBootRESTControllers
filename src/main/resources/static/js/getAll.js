$(document).ready(function () {
    getAllUsers();
});

function getAllUsers() {
    $.ajax({
        url: "/api/users",
        method: "GET",
        dataType: "json",
        success: function (data) {
            var tableBody = $('#tableUsersGetAll tbody');
            tableBody.empty();
            $(data).each(function (i, user) {
                var stringRoles = "";
                $(user.roles).each(function (i, role) {
                    stringRoles += role.role + " ";
                });
                tableBody.append(`<tr> 
                    <td>${user.name}</td> 
                    <td>${user.login}</td> 
                    <td>${user.password}</td> 
                    <td>${stringRoles}</td> 
                    <td><button  id="deleteButton" class="btn btn-danger" role="button" onclick = "deleteUser(${user.id})"> 
                    Delete 
                    </button></td> 
                    <td><button id="updateButton" class="btn btn-info" role="button" data-toggle="modal" 
                    data-target="#exampleModal" onclick = "fillUpdateModalForm(${user.id})"> 
                    Update 
                    </button></td> 
                    </tr>`);
            })
        },
        error: function (error) {
            alert(error);
        }
    })
}