function deleteUser(id) {
    $.ajax({
        url: '/api/users/' + id,
        method: 'DELETE',
        success: function () {
            getAllUsers();
        },
        error: function (error) {
            alert(error);
        }
    })
}