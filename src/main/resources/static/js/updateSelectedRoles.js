$(document).ready(function () {
    getAllRolesForUpdateForm();
});

function getAllRolesForUpdateForm() {
    $.ajax({
        url: "/api/roles",
        method: "GET",
        dataType: "json",
        success: function (data) {
            var selectBody = $('#modalAuthority');
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
