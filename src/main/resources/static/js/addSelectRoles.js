$(document).ready(function () {
    getAllRolesForAddForm();
});

function getAllRolesForAddForm() {
    $.ajax({
        url: "/api/roles",
        method: "GET",
        dataType: "json",
        success: function (data) {
            var selectBody = $('#authorityAddUser');
            $(data).each(function (i, role) {
                selectBody.append(`
                <option value="${role.id}" >${role.role}</option>
                `);
            })
        },
        error: function (error) {
            alert(error);
        }
    })
}
