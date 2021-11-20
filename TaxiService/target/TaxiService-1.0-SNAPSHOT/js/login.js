$(document).ready(function () {
    const form = $("#signIn")

    form.on('submit', function () {

        const phone = $("#phone").val();
        const password = $("#password").val();

        if (phone === "" || password === "") {
            alert('Check your inputs please!')
            return false
        }

        $.ajax(
            {
                url: '/sign-in',
                method: 'POST',
                data: {
                    phoneNumber: phone,
                    password: password
                },
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }
        ).done(function () {
            window.location = '/profile'
        }).fail(function (data) {
            console.log(data)
            alert(data.responseJSON.message)
        })
        return false
    })
})