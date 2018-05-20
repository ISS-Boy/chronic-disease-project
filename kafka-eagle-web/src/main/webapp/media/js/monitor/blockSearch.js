
    function add_block() {
        var template = $('#blockTmpl').tmpl().appendTo('.block')
        initFunc($('.block').children().last())
        $('.selectpicker').selectpicker({
        'selectedText': 'cat'
        })
    }
    function remove_block() {
        var blockLength = $('.block').children().length;
        if(blockLength > 1)
        $(".block").children().last().remove();
    }

    function add_aggregation(span) {
        var this_block = $(span).parents('.block_tmpl');
        if(!checkTopicEmpty(this_block))
            return;

        var pBlock = $(span).parents('.block_tmpl')
        var aggregationsI = pBlock.find($('.aggregation_item_outpanel')).children();
        var length = aggregationsI.length;
        console.log(length);
        if(length == 0){
           var windowRow =  this_block.find($('.window_row_tmpl'));
           console.log(windowRow);
           $('#aggregationWindowTmpl').tmpl().appendTo(windowRow);
        }

        var aggregation = this_block.find($('.aggregation_item_outpanel'));
        $('#aggregationTmpl').tmpl().appendTo(aggregation);
        $('.selectpicker').selectpicker({
            'selectedText': 'cat'
        });

        onCalNameDefine(this_block, this_block.find($('.aggregation_item_outpanel:last-child input[name="a_name"]')));
        addListenerForBlock(this_block, '.aggregation_item_outpanel', 'a_source');
        addListenerForBlock(this_block, '.aggregation_item_outpanel', 'g_source');
    }
    function remove_aggregation(span){
        var pBlock = $(span).parents('.block_tmpl')

        var aggregationsI = pBlock.find($('.aggregation_item_outpanel')).children();
        var length = aggregationsI.length;
        console.log(length);
        if (length < 2)
            $(span).parents('.block_tmpl').find(".window_").remove();
        $(span).parents('.aggregation_item_templ').remove();

        // 手动触发事件更新内容
        var selectSources = pBlock.find($('.select select[name="s_source"]'))
        var filterSources = pBlock.find($('.filter select[name="f_source"]'))
        selectSources.each(function () {
            var measureBlock = $(this).parent().parent().next().find('select')
            changeContent($(this), measureBlock)
        })
        filterSources.each(function () {
            var measureBlock = $(this).parent().parent().next().find('select')
            changeContent($(this), measureBlock)
        })
    }

    function add_aggregation_predicate(span) {
        var this_block = $(span).parents('.block_tmpl')
        if(!checkTopicEmpty(this_block))
            return;
        var aggregation_group = $(span).parents('.aggregation_group_panel').find($('.aggregation_group'));
        var aggregations = aggregation_group.children();
        var length = aggregations.length;
        console.log(length);
        if(length==0)
            $('.aggregation_group').css("border","1px solid #eee");
        $('#calculationTmpl').tmpl().appendTo(aggregation_group);
        $('.selectpicker').selectpicker({
        'selectedText': 'cat'  
        });
        // 为input添加响应事件
        addListenerForBlockWithElement(this_block, aggregation_group, 'g_source')
    }

    function remove_aggregation_predicate(span) {
        var aggregations = $(span).parents('.aggregation_group_panel').find($('.aggregation_group')).children();
        var length = aggregations.length;
        console.log(length);
        if (length < 2)
            $('.aggregation_group').css("border","none");
        $(span).parents('.aggregation_group_templ').remove();
    }

    function add_filter(span) {
        var this_block = $(span).parents('.block_tmpl');
        if(!checkTopicEmpty(this_block))
            return
        var filter = this_block.find($('.filter'));
        $('#filterTempl').tmpl().appendTo(filter);
        $('.selectpicker').selectpicker({  
        'selectedText': 'cat'  
        })

        addListenerForBlock(this_block, '.filter', 'f_source')
    }

    function remove_filter(span){
        var filter = $(span).parents('.block_tmpl').find($('.filter'));
        // filter.children().last().remove()
        $(span).parents('.filter_templ').remove();
    }

    function add_select(span) {
        var this_block = $(span).parents('.block_tmpl');
        if(!checkTopicEmpty(this_block))
            return
        var select = this_block.find($('.select'));
        $('#selectTempl').tmpl().appendTo(select);
        $('.selectpicker').selectpicker({  
        'selectedText': 'cat'  
        })

        addListenerForBlock(this_block, '.select', 's_source')

    }
    function remove_select(span){
        var selects = $(span).parents('.block_tmpl').find($('.select')).children();
        // var index = $(span).index();
        // console.log()
        if(selects.length > 1)
            $(span).parent().parent().remove();

    }
////////////-----------------------------///////////////////////
    //data封装
    $(function (){
        $('.block_submit').click(function(){
            var blockGroupName = $('.blockGroupName').val()
            var monitorName = $('.monitorName').val()
            var w_interval = $('.block_tmpl').find($('.window_row')).children("input[name='w_interval']").val()
            var w_length = $('.block_tmpl').find($('.window_row')).children("input[name='w_length']").val()
            var f_threshlod = $('.filter_templ').children().children("input[name='f_threshold']").val()
            var g_threshold = $('.aggregation_group_templ').children().children("input[name='g_threshold']").val()
            if(blockGroupName.length==0||monitorName.length==0){
                alert("命名不能为空！")
                return false
            // }
            // else if(w_interval.length==0||w_length.length==0||f_threshlod.length==0||g_threshold.length==0){
            //     alert("数字输入不能为空！");
            //     return false
            }else {
            // 遍历每个block块
                var blockGroups = {}
                var blockValues = []
                var empty = false
                var blockGroupName = $('.blockGroupName').val()
                blockGroups.blockGroupName = blockGroupName

                $('.block_tmpl').each(function () {
                    var this_block = $(this)

                    // 首先做一下校验source空检验
                    if(!checkTopicEmpty(this_block)) {
                        empty = true
                        return
                    }

                    // 一个monitor对应一个panel, 一个block对象对应一个block内的所有信息
                    var block = {};

                    //获取mornitor名字
                    var monitorName = this_block.find($('.monitorName')).val()
                    block.monitorName = monitorName


                    var sourceJson = []
                    var sourceList = this_block.find($('.source select option:selected'))

                    // 获取source键值对
                    sourceList.each(function () {
                        var source= {
                            sourceName: localizeDictionary[$(this).text()]
                        }
                        sourceJson.push(source)
                    })

                    console.log(sourceJson)

                    // 获取window键值对
                    var aggregation = {}
                    var aggregationWindow = {
                        windowType: localizeDictionary[this_block.find($('.window_row')).children().children("select").val()],
                        windowInterval: this_block.find($('.window_row')).children("input[name='w_interval']").val(),
                        windowLength: this_block.find($('.window_row')).children("input[name='w_length']").val()
                    }

                    console.log(aggregationWindow)

                    // 获取aggregation键值对
                    var aggregationValues = []
                    var aggregationList = this_block.find($('.aggregation_item_templ'))
                    aggregationList.each(function(){
                        var predicatesArray = []
                        var predicates = $(this).find($('.aggregation_group_templ'))
                        predicates.each(function () {
                            var predicate = {
                                source: localizeDictionary[$(this).find("select[name='g_source']").val()],
                                measure: localizeDictionary[$(this).find("select[name='g_measure']").val()],
                                op: $(this).find("select[name='g_op']").val(),
                                threshold: $(this).find("input[name='g_threshold']").val(),
                                boolExp: localizeDictionary[$(this).find("select[name='g_boolExp']").val()]
                            }
                            predicatesArray.push(predicate)
                        })

                        var aggr = {
                            name: $(this).find("input[name='a_name']").val(),
                            source: localizeDictionary[$(this).find("select[name='a_source']").val()],
                            measure: localizeDictionary[$(this).find("select[name='a_measure']").val()],
                            type: localizeDictionary[$(this).find("select[name='a_type']").val()],
                            predicates: predicatesArray
                        }
                        aggregationValues.push(aggr)
                    })
                    aggregation.window = aggregationWindow
                    aggregation.aggregationValues = aggregationValues
                    console.log(aggregation)

                    // 获取filter键值对
                    var filters = []
                    var filterList = this_block.find($('.filter_templ'))
                    filterList.each(function(){
                        var measure = $(this).children().children().children("select[name='f_measure']").val();
                        if(localizeDictionary[measure] != undefined)
                            measure = localizeDictionary[measure]
                        var filter = {
                            f_source: localizeDictionary[$(this).children().children().children("select[name='f_source']").val()],
                            f_measure: measure,
                            f_op: $(this).children().children().children("select[name='f_op']").val(),
                            f_threshold: $(this).children().children("input[name='f_threshold']").val(),
                            f_boolExp: localizeDictionary[$(this).children().children().children("select[name='f_boolExp']").val()]
                        }
                        //console.log(filter)
                        filters.push(filter)
                    })
                    console.log(filters)
                    // 获取select键值对
                    var selects = []
                    var alertOnly = this_block.find($('input[type="checkbox"]')).prop('checked')
                    // 若不是只看警报, 则添加所有select和measure项
                    if(!alertOnly) {
                        var selectList = this_block.find($('.select_templ'))
                        selectList.each(function () {
                            var meaOrCal = $(this).children().children().children("select[name='s_meaOrCal']").val();
                            if (localizeDictionary[meaOrCal] != undefined)
                                meaOrCal = localizeDictionary[meaOrCal]
                            var select = {
                                s_source: localizeDictionary[$(this).children().children().children("select[name='s_source']").val()],
                                s_meaOrCal: meaOrCal
                            };
                            //console.log(select)
                            selects.push(select)
                        });
                    }else{
                        var select = {
                            s_source: '1',
                            s_meaOrCal: '1'
                        }
                        selects.push(select)
                    }
                    console.log(selects)

                    block.source  = sourceJson
                    block.aggregation = aggregation
                    block.filters = filters
                    block.selects = selects
                    console.log(block)
                    blockValues.push(block)
                })

                // 封装最后的blockValues
                blockGroups.blockValues = blockValues

                if(empty)
                    return;

                console.log(blockGroups)
                console.log(JSON.stringify(blockGroups))

                // 出现加载等待进度条
                $('.loading').show()
                $('.cover').show()

                var urls = []
                var block_size = $('.block_tmpl').length
                $('.block_tmpl').each(function(){
                    var targetDom = $(this)
                    var copyDom = targetDom.clone();
                    $('#hideCopy').append(copyDom);
                    html2canvas(copyDom, {
                        onrendered: function(canvas) {
                            canvas.id = "mycanvas";

                            //生成base64图片数据
                            var url = canvas.toDataURL();
                            urls.push(url)
                            if(urls.length == block_size){
                                $('#hideCopy').children().remove()
                                for(var i in urls){
                                    blockGroups.blockValues[i].imgUrl = urls[i]
                                }

                                $.ajax({
                                    type: "POST",
                                    url: "/ke/monitor/submitMonitors",
                                    contentType:'application/json',
                                    data: JSON.stringify(blockGroups),
                                    success:function(data){
                                        $('.loading').hide()
                                        $('.cover').hide()

                                        console.log(data)
                                        if(data == "success")
                                            window.location.href = "/ke/monitor/monitor_maintain"
                                        else
                                            alert(data)
                                    },
                                    error:function(textStatus, errorThrown){
                                        $('.loading').hide()
                                        $('.cover').hide()

                                        console.log(textStatus)
                                        console.log(errorThrown)
                                    }
                                })
                            }

                        }//此处可以放参数：width : wid , height : hei*2
                    });
                })
            }
        })
        $('.block_reset').click(function(){
            var blockLength = $('.block').children().length;
            for (var i = 0;i<blockLength-1;i++) {
                if(blockLength>1)
                    $(".block").children().last().remove();
            }
        })
    })