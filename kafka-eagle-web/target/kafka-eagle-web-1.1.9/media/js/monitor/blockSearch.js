
    function add_block() {
        var template = $('#blockTmpl').tmpl().appendTo('.block')
        initFunc($('.block').children().last())
        $('.selectpicker').selectpicker({
        'selectedText': 'cat'
        })

    }
    function remove_block() {
        var blockLength = $('.block').children().length
        if(blockLength > 1)
        $(".block").children().last().remove()
    }


    function add_calculation(span) {
        var this_block = $(span).parents('.block_tmpl')
        if(!checkTopicEmpty(this_block))
            return
        var calculation = this_block.find($('.calculation'))
        $('#calculationTmpl').tmpl().appendTo(calculation)
        $('.selectpicker').selectpicker({  
        'selectedText': 'cat'  
        })

        // 为input添加响应事件
        onCalNameDefine(this_block, this_block.find($('.calculation_templ:last-child input[name="c_name"]')))
        addListenerForBlock(this_block, '.calculation', 'c_source')
    }

    function remove_calculation(span) {
        var calculation = $(span).parents('.block_tmpl').find($('.calculation'))
        calculation.children().last().remove()
    }

    function add_filter(span) {
        var this_block = $(span).parents('.block_tmpl')
        if(!checkTopicEmpty(this_block))
            return
        var filter = this_block.find($('.filter'))
        $('#filterTempl').tmpl().appendTo(filter)
        $('.selectpicker').selectpicker({  
        'selectedText': 'cat'  
        })

        addListenerForBlock(this_block, '.filter', 'f_source')
    }

    function remove_filter(span){
        var filter = $(span).parents('.block_tmpl').find($('.filter'))
        filter.children().last().remove()
    }

    function add_select(span) {
        var this_block = $(span).parents('.block_tmpl')
        if(!checkTopicEmpty(this_block))
            return
        var select = this_block.find($('.select'))
        $('#selectTempl').tmpl().appendTo(select)
        $('.selectpicker').selectpicker({  
        'selectedText': 'cat'  
        })

        addListenerForBlock(this_block, '.select', 's_source')

    }
    function remove_select(span){
        var selects = $(span).parents('.block_tmpl').find($('.select')).children()
        if(selects.length > 1)
            selects.last().remove()

    }
////////////-----------------------------///////////////////////
    //data封装
    $(function (){
        $('.block_submit').click(function(){
            // 遍历每个block块
            var blockGroups = []
            var empty = false
            $('.block_tmpl').each(function () {

                var this_block = $(this)

                // 首先做一下校验source空检验
                if(!checkTopicEmpty(this_block)) {
                    empty = true
                    return
                }

                if(this_block.find($('.calculation .window_ [name=interval]')))

                var sourceJson = []
                //var block = new Object()
                var sourceList = this_block.find($('.source select option:selected'))

                // 获取source键值对
                sourceList.each(function () {
                    var source= {
                        sourceName: $(this).text()
                    }
                    sourceJson.push(source)

                })

                console.log(sourceJson)

                // 获取window键值对
                var calculation = {}
                var c_window = {
                    w_type: this_block.find($('.window_row')).children().children("select").val(),
                    w_interval: this_block.find($('.window_row')).children("input[name='w_interval']").val(),
                    w_length: this_block.find($('.window_row')).children("input[name='w_length']").val()
                }
                console.log(c_window)
                calculation.window = c_window

                // 获取calcution键值对
                var calculationValues = []
                var calculationList = this_block.find($('.calculation_templ'))
                calculationList.each(function(){
                    var cal = {
                        c_name:$(this).children().children("input[name='c_name']").val(),
                        c_source:$(this).children().children().children("select[name='c_source']").val(),
                        c_measure:$(this).children().children().children("select[name='c_measure']").val(),
                        c_type:$(this).children().children().children("select[name='c_type']").val()
                    }
                    //console.log(calculation)
                    calculationValues.push(cal)
                })
                calculation.calculationValues = calculationValues
                console.log(calculation)

                // 获取filter键值对
                var filters = []
                var filterList = this_block.find($('.filter_templ'))
                filterList.each(function(){
                    var filter = {
                        f_source:$(this).children().children().children("select[name='f_source']").val(),
                        f_measure:$(this).children().children().children("select[name='f_measure']").val(),
                        f_op:$(this).children().children().children("select[name='f_op']").val(),
                        f_threshold:$(this).children().children("input[name='f_threshold']").val(),
                        f_boolExp:$(this).children().children().children("select[name='f_boolExp']").val()
                    }
                    //console.log(filter)
                    filters.push(filter)
                })
                console.log(filters)
                // 获取select键值对
                var selects = []
                var selectList = this_block.find($('.select_templ'))
                selectList.each(function(){
                    var select = {
                        s_source:$(this).children().children().children("select[name='s_source']").val(),
                        s_meaOrCal:$(this).children().children().children("select[name='s_meaOrCal']").val(),
                    }
                    //console.log(select)
                    selects.push(select)
                })
                console.log(selects)
                var block = {}
                block.source  = sourceJson
                block.calculation = calculation
                block.filters = filters
                block.selects = selects
                console.log(block)
                blockGroups.push(block)
            })

            if(empty)
                return;

            console.log(blockGroups)

            // 出现加载等待进度条
            $('.loading').show()

            $.ajax({
                type: "POST",
                url: "/ke/monitor/submitMonitors",
                contentType:'application/json',
                data: JSON.stringify(blockGroups),
                success:function(data){
                    $('.loading').hide()
                    console.log(data)
                    if(data == "success")
                        window.location.href = "/ke/monitor/monitor_maintain"
                    else
                        alert(data)
                },
                error:function(textStatus, errorThrown){
                    $('.loading').hide()
                    console.log(textStatus)
                    console.log(errorThrown)
                }
            })
        })
    })