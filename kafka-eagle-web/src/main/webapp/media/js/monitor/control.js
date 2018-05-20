// 规定全局字典数据
// var metadata = {
//     'blood-pressure': new Set(['systolic_blood_pressure', 'diastolic_blood_pressure']),
//     'body-fat-percentage': new Set(['body_fat_percentage']),
//     'heart-rate': new Set(['heart_rate']),
//     'body-temperature': new Set(['body_temperature'])
// }

var metadata = {
    '血压': new Set(['收缩压.', '舒张压.']),
    '体脂': new Set(['体脂.']),
    '心率': new Set(['心率.']),
    '体温': new Set(['体温.'])
}

var localizeDictionary = {
    '血压': 'blood-pressure',
    '收缩压.': 'systolic_blood_pressure',
    '舒张压.': 'diastolic_blood_pressure',
    '体脂': 'body-fat-percentage',
    '体脂.': 'body_fat_percentage',
    '心率': 'heart-rate',
    '心率.': 'heart_rate',
    '体温': 'body-temperature',
    '体温.': 'body_temperature',
    '滑动窗口': 'sliding-window',
    '翻滚窗口': 'tumbling-window',
    '跳跃窗口': 'hopping-window',
    '最小值': 'min',
    '最大值': 'max',
    '平均值': 'average',
    '计数': 'count',
    '求和': 'sum',
    '增幅': 'amplitude',
    '增比': 'rate',
    '且': 'and',
    '或': 'or'
}

// 初始化被选中的source
$(document).ready(function () {
    var block = $('.block_tmpl')
    block.each(function () {
        initFunc($(this))
    })
})


function initFunc(new_block){
    var selects = new_block.find($(".source select"))
    selects.each(function () {
            $(this).empty()
            var t = $(this)
            $.each(metadata, function (k, _) {
                t.append("<option value='" + k + "'>" + k + "</option>")
            })
        }
    )
    selects.selectpicker('refresh')

    // 为SourceSelect创建更改事件监听
    new_block.find($('.source select')).each(function () {
        $(this).change(function () {
            var selectedOptions = []
            $(this).find("option:selected").each(function () {
                selectedOptions.push($(this).text())
            })
            // 更新其它块中的source记录
            update(selectedOptions, new_block, ".aggregation_item", "a_source", "a_measure")
            update(selectedOptions, new_block, ".aggregation_group", "g_source", "g_measure")
            update(selectedOptions, new_block, ".filter", "f_source", "f_measure")
            update(selectedOptions, new_block, ".select", "s_source", "s_meaOrCal")
        })
    })

    // 为其它Block的source和measure创建联动事件
    readyForBlock(new_block, ".aggregation_item", "a_source")
    readyForBlock(new_block, ".aggregation_group", "g_source");
    readyForBlock(new_block, ".filter", "f_source")
    readyForBlock(new_block, ".select", "s_source")

    // 为Calculation块的name输入框创建联动事件
    onCalNameDefine(new_block)

    // 为复选框添加事件
    readyForCheckBox(new_block)
}

function readyForCheckBox(this_block){
    this_block.find($('input[type="checkbox"]')).change(function () {
        var select_block = $(this).parents(".select_parent")
        var shouldDisabled = $(this).prop('checked')
        select_block.find($('select')).each(function () {
            $(this).attr('disabled', shouldDisabled)
            $(this).selectpicker('refresh')
        })

        var add = select_block.find($('span[name="add_select_block"]'))
        if(shouldDisabled)
            add.attr('onclick', "")
        else
            add.attr('onclick', 'add_select(this)')
        add.selectpicker('refresh')
    })
}

// 为source和measure创建联动事件
function readyForBlock(this_block, blockClass, sourceName){
    this_block.find($(blockClass + ' [name="'+ sourceName +'"]')).each(function () {
        var measure = $(this).parent().next().find("select")
        onchange($(this), measure)
    })
}

// 更新各控件
function update(selectedOptions, this_block, block_class, sourceName, measureName) {
    // 找到对应的select块
    var selects = this_block.find($(block_class + ' select'))
    selects.each(function () {
        var t = $(this)
        var name = t.attr('name')
        if (name == sourceName) {
            $(this).empty()
            $(selectedOptions).each(function () {
                t.append("<option value='" + this + "'>" + this + "</option>")
            })
        }
        else if (name == measureName) {
            $(this).empty()
            var sourceBlock = t.parent()
                    .parent()
                    .prev()
                    .find("select")
            var source = sourceBlock.find("option:selected")
                                    .first().text()

            if(source != ""){
                // 把原有metadata加入到option项下
                metadata[source].forEach(function (value) {
                    t.append("<option value='" + value + "'>" + value + "</option>")
                })

                // 找到所有的新增的calculation名称
                if(sourceBlock.attr('name') != 'a_source' && sourceBlock.attr('name') != 'g_source'){
                    var calRoot = sourceBlock.parents('.block_tmpl').find($('.aggregation_item_templ'))
                    calRoot.each(function () {
                        var cSource = $(this).find($('[name="a_source"] option:selected'))
                        if(source == cSource.text()){
                            var calName = $(this).find($('input')).val();
                            t.append("<option value='" + calName + "'>" + calName + "</option>")
                        }
                    })
                }
            }


        }
    })

    selects.selectpicker('refresh')
}

// 创建触发函数
function onchange(sourceBlock, measureBlock){
    sourceBlock.change(function () {
        var source = $(this).find("option:selected").first().text()
        measureBlock.empty()

        metadata[source].forEach(function (value) {
            measureBlock.append("<option value='" + value + "'>" + value + "</option>")
        })

        // 添加
        if($(this).attr('name') != "a_source" && $(this).attr('name') != "g_source"){
            var calRoot = sourceBlock.parents('.block_tmpl').find($('.aggregation_item_templ'))
            calRoot.each(function () {
                var cSource = $(this).find($('[name="a_source"] option:selected'))
                if(source == cSource.text()){
                    var calName = $(this).find($('input')).val();
                    measureBlock.append("<option value='" + calName + "'>" + calName + "</option>")
                }
            })
        }else{
            // 手动触发事件更新内容
            var pBlock = $(this).parents('.block_tmpl')
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
        measureBlock.selectpicker('refresh')
    })
}

function changeContent(sourceBlock, measureBlock) {
    var source = sourceBlock.find("option:selected").first().text()
    measureBlock.empty()

    metadata[source].forEach(function (value) {
        measureBlock.append("<option value='" + value + "'>" + value + "</option>")
    })

    if(sourceBlock.attr('name') != 'a_source' && sourceBlock.attr('name') != "g_source"){
        var calRoot = sourceBlock.parents('.block_tmpl').find($('.aggregation_item_templ'))
        calRoot.each(function () {
            var cSource = $(this).find($('[name="a_source"] option:selected'))
            if(source == cSource.text()){
                var calName = $(this).find($('input')).val();
                if (calName != "")
                    measureBlock.append("<option value='" + calName + "'>" + calName + "</option>")
            }
        })
    }
    measureBlock.selectpicker('refresh')
}

// 为添加aggregationName增加触发事件
function onCalNameDefine(this_block, nameBlock){
    // 如果没有定义nameBlock的话, 直接进行所有nameBlock的工作
    if(nameBlock == undefined)
        nameBlock = $(this_block).find($('.calculation input[name="a_name"]'))
    nameBlock.blur(function () {
        var pBlock = $(this).parents('.block_tmpl')
        // 命名不允许和现有命名冲突
        if(localizeDictionary[$(this).val()] != undefined){
            alert("命名与现有命名冲突, 请更换您的命名")
            $(this).val("")
            return false
        }

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
    })
}

function addListenerForBlockWithElement(this_block, curElement, blockSourceName){
    var sourceSelected = this_block.find($(".source select"))
    var sources = sourceSelected.children('option:selected')
    // 添加选中元素
    var sourceBlock = curElement.find($('select[name="' + blockSourceName + '"]')).last()
    sourceBlock.empty()
    sources.each(function () {
        sourceBlock.append("<option value='" + $(this).text() + "'>" + $(this).text() + "</option>")
    })
    sourceBlock.selectpicker('refresh')

    var measureBlock = sourceBlock.parent().parent().next().find('select')

    // 创建触发函数
    onchange(sourceBlock, measureBlock)

    // 依据source里的值修改measure的值
    changeContent(sourceBlock, measureBlock)
}

function addListenerForBlock(this_block, blockClassName, blockSourceName) {
    var sourceSelected = this_block.find($(".source select"))
    var sources = sourceSelected.children('option:selected')
    var curBlock = this_block.find($(blockClassName)).children().last()
    // 添加选中元素
    var sourceBlock = curBlock.find($('select[name="' + blockSourceName + '"]'))
    sourceBlock.empty()
    sources.each(function () {
        sourceBlock.append("<option value='" + $(this).text() + "'>" + $(this).text() + "</option>")
    })
    sourceBlock.selectpicker('refresh')

    // var sourceVal = sourceBlock.find("option:selected").text()
    var measureBlock = sourceBlock.parent().parent().next().find('select')

    // 创建触发函数
    onchange(sourceBlock, measureBlock)

    // 依据source里的值修改measure的值
    changeContent(sourceBlock, measureBlock)
}

function checkTopicEmpty(this_block) {
    var sourceSelected = this_block.find($(".source select"))
    var sources = sourceSelected.children('option:selected')
    if (sources.length < 1) {
        alert("请先选择数据源")
        return false
    }
    return true
}