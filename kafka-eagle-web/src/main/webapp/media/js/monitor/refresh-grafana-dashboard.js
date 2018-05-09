$(document).ready(function () {
    $('iframe').each(function () {
        var iframe = $(this)
        $(this).everyTime('5s', function () {
            var now = new Date()
            var nowTime = now.valueOf()
            var fromTime = new Date(nowTime - 2 * 60 * 60 * 1000)
            var from = fromTime.getFullYear() + '-' +
                (fromTime.getMonth() + 1) + '-' +
                fromTime.getDate() + 'T' +
                fromTime.getHours() + ':' +
                fromTime.getMinutes() + ':' +
                fromTime.getSeconds() + 'Z'
            var to = now.getFullYear() + '-' +
                (now.getMonth() + 1) + '-' +
                now.getDate() + 'T' +
                now.getHours() + ':' +
                now.getMinutes() + ':' +
                now.getSeconds() + 'Z'

            alert(from)
            alert(to)
            var src = iframe.attr('src')
            src = src.replace(/from=.*?&/, 'from=' + from + '&')
            src = src.replace(/to=.*?&/, 'to=' + to + '&')
            iframe.attr('src', src)
        })
    })
})