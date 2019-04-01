function fillUpdateModalForm(id) {
    $.ajax({
        url: '/api/users/' + id,
        method: "GET",
        dataType: "json",
        success: function (data) {
            $('#modalId').val(data.id);
            $('#modalName').val(data.name);
            $('#modalLogin').val(data.login);
            $('#modalPassword').val(data.password);
        },
        error: function (error) {
            alert(error);
        }
    })
}