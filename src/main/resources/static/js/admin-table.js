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