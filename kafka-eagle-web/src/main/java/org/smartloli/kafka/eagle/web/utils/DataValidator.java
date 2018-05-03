package org.smartloli.kafka.eagle.web.utils;

import org.smartloli.kafka.eagle.web.json.pojo.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dujijun on 2018/3/27.
 */
public class DataValidator {

    /**
     * 主要进行数据校验，若出现数据不规范的情况，则返回相应错误信息
     * 校验结果主要有三种：SUCCESS, ERROR, FAILURE
     * 1、如果calculation中的数据没有被filter和select使用，则warning，提示未使用的变量
     * 2、如果
     * @param blockGroup 一个块组
     */
    public static List<ValidateResult> validateBlocks(BlockGroup blockGroup){
        List<ValidateResult> validateResults = new ArrayList<>();
        List<BlockValues> blocks = blockGroup.getBlockValues();
        for(int i = 0; i < blocks.size(); i++){
            BlockValues block = blocks.get(i);

            // 判别是否有未使用过的计算值
            if(!checkUnusedAggregation(block)){
                validateResults.add(new ValidateResult(ValidateResult.ResultCode.WARNING,
                                                       String.format("block-%d: 未使用的计算值", i)));
            }

            // 判别前端的命名选择是否有重复
            if(!checkDuplicated(block)){
                validateResults.add(new ValidateResult(ValidateResult.ResultCode.WARNING,
                                                       String.format("block-%d: 存在重复命名", i)));
            }
        }

        if(validateResults.size() == 0)
            validateResults.add(new ValidateResult(ValidateResult.ResultCode.SUCCESS, "校验成功"));
        return validateResults;

    }

    /** 检查是否存在命名重复使用的情况 */
    private static boolean checkDuplicated(BlockValues block){
        int aggrNameSetSize = block.getAggregation()
                .getAggregationValues()
                .stream().map(AggregationValues::getName)
                .collect(Collectors.toSet())
                .size();

        int aggrNameSize = block.getAggregation()
                .getAggregationValues()
                .size();

        int filNameSetSize = block.getFilters()
                .stream()
                .map(Filters::getF_measure)
                .collect(Collectors.toSet()).size();

        int filNameSize = block.getFilters().size();

        int selectNameSetSize = block.getSelects()
                .stream()
                .map(Selects::getS_meaOrCal)
                .collect(Collectors.toSet()).size();

        int selectNameSize = block.getSelects().size();

        return aggrNameSetSize == aggrNameSize &&
                selectNameSetSize == selectNameSize &&
                filNameSetSize == filNameSize;
    }

    /** 判别在aggregation中声明的变量是否被filter和select使用 */
    private static boolean checkUnusedAggregation(BlockValues block){
        List<AggregationValues> aggregationValues = block.getAggregation().getAggregationValues();
        List<Selects> selects = block.getSelects();
        List<Filters> filters = block.getFilters();
        Set<String> aggrName = new HashSet<>();
        Set<String> selNames = new HashSet<>();
        Set<String> filNames = new HashSet<>();
        Set<String> allUsedNames = new HashSet<>();
        aggregationValues.forEach(a -> aggrName.add((a.getName())));
        filters.forEach(f -> filNames.add(f.getF_measure()));
        selects.forEach(s -> selNames.add(s.getS_meaOrCal()));
        allUsedNames.addAll(filNames);
        allUsedNames.addAll(selNames);
        return allUsedNames.containsAll(aggrName);
    }
}
