$('#deleteSubj').on('click', function(e) {
    e.preventDefault();
    let form = $('#deleteSubj');
    $.ajax({
        url: form.attr('action'),
        data: form.serialize(),
        type: "post",
        success: function(result) {
            // Do something with the response.
            // Might want to check for errors here.
            location.reload();
        }, error: function(error) {
            // Here you can handle exceptions thrown by the server or your controller.
        }
    })
});

function deleteSubject(id) {
    console.log("here");
    $.ajax({
        url: "localhost:8080/api/user/1/admin/subject/delete/" + id,
        type: "post",
        success: function() {
            // Do something with the response.
            // Might want to check for errors here.
            location.reload();
        }, error: function(error) {
            // Here you can handle exceptions thrown by the server or your controller.
        }
    });
}

function redirectSpec() {
    console.log("here");
    window.location.href = "http://localhost:8080/api/user/1/admin/schedule?speciality=" + $("#inputSpec option:selected").text();
    // $.ajax({
    //     url: "http://localhost:8080/api/admin/schedule?speciality=" + $("#inputSpec option:selected").text(),
    //     type: "get",
    //     success: function() {
    //         // Do something with the response.
    //         // Might want to check for errors here.
    //         // location.reload();
    //
    //     }, error: function(error) {
    //         // Here you can handle exceptions thrown by the server or your controller.
    //     }
    // });
}