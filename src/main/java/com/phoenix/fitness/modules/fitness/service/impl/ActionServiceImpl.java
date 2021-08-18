package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import com.phoenix.fitness.modules.fitness.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.form.ActionSearchForm;
import com.phoenix.fitness.modules.fitness.dao.ActionDao;
import com.phoenix.fitness.modules.fitness.dao.ActionEquipmentDao;
import com.phoenix.fitness.modules.fitness.dao.ActionExampleDao;
import com.phoenix.fitness.modules.fitness.dao.ActionTypeDao;
import com.phoenix.fitness.modules.fitness.dto.ActionTypeWithActionsDto;
import com.phoenix.fitness.modules.fitness.dto.ActionWithActionTypeDto;
import com.phoenix.fitness.modules.fitness.dto.ActionWithAllDto;
import com.phoenix.fitness.modules.fitness.entity.*;
import com.phoenix.fitness.modules.fitness.service.ActionEquipmentService;
import com.phoenix.fitness.modules.fitness.service.ActionExampleService;
import com.phoenix.fitness.modules.fitness.service.ActionService;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("actionService")
@AllArgsConstructor
public class ActionServiceImpl extends ServiceImpl<ActionDao, ActionEntity> implements ActionService {

    private final ActionTypeDao actionTypeDao;

    private final ActionDao actionDao;

    private final ActionExampleService actionExampleService;

    private final ActionExampleDao actionExampleDao;

    private final ActionEquipmentService actionEquipmentService;

    private final ActionEquipmentDao actionEquipmentDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params, UserEntity user) {
        ActionSearchForm actionSearchForm = new ActionSearchForm();
        if (!StringUtils.isEmpty(params.get("actionName"))) {
            actionSearchForm.setActionName((String) params.get("actionName"));
        }
        if (!StringUtils.isEmpty(params.get("actionTypeId"))) {
            actionSearchForm.setActionTypeId(Long.parseLong((String) params.get("actionTypeId")));
        }
        if (!StringUtils.isEmpty(params.get("containWeight"))) {
            actionSearchForm.setContainWeight(Integer.parseInt((String)params.get("containWeight")));
        }
        if (!StringUtils.isEmpty(params.get("unit"))) {
            actionSearchForm.setUnit((String) params.get("unit"));
        }
        IPage<ActionWithActionTypeDto> pageParams = new Query<ActionWithActionTypeDto>().getPage(params);
        IPage<ActionEntity> actionPage = actionDao.selectActionWithActionType(pageParams, actionSearchForm, user);
        List<ActionEntity> actions = actionPage.getRecords();
        List<ActionEntity> finalActionList = new ArrayList<>();
        for (ActionEntity action : actions) {
            ActionEntity temp = actionDao.selectActionWithAll(action.getActionId());
            finalActionList.add(temp);
        }
        actionPage.setRecords(finalActionList);
        return new PageUtils(actionPage);
    }

    @Override
    public List<ActionTypeWithActionsDto> getActionsWithType(Map<String, Object> params, UserEntity user) {
        String actionName = "";
        if (params.get("searchValue") != null) {
            actionName = params.get("searchValue").toString();
        }
        return actionTypeDao.selectActionTypeWithAction(actionName, user);
    }

    @Override
    public ActionWithAllDto getDetail(Long id) {
        ActionWithAllDto action = actionDao.selectActionWithAll(id);
        if (action == null || action.getDeleteFlag().equals(1)) {
            //根据id未查询到内容
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return action;
    }

    @Override
    public Boolean save(ActionWithAllDto action) {
        //检查动作分类是否存在
        this.checkActionType(action);
        int count = actionDao.insert(action);
        if (count == 0) {
            //新增失败
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        this.saveOrUpdateBase(action);
        return true;
    }

    @Override
    public Boolean updateById(ActionWithAllDto action) {
        //检查动作分类是否存在
        this.checkActionType(action);
        //检查要修改的内容是否存在
        ActionEntity temp = actionDao.selectById(action.getActionId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //要修改的内容不存在
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = actionDao.updateById(action);
        if (count == 0) {
            //修改失败
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        this.saveOrUpdateBase(action);
        return true;
    }

    public void checkActionType(ActionWithAllDto action) {
        ActionTypeEntity actionType = actionTypeDao.selectById(action.getActionType().getActionTypeId());
        if (actionType == null || actionType.getDeleteFlag().equals(1)) {
            //动作分类不存在或已删除
            throw new FitnessException(ExceptionEnum.ACTION_TYPE_NOT_FOUND);
        }
        action.setActionTypeId(action.getActionType().getActionTypeId());
    }

    public void saveOrUpdateBase(ActionWithAllDto action) {
        //先删除掉所有的actionExamples
        actionExampleDao.delete(new QueryWrapper<ActionExampleEntity>().eq("action_id", action.getActionId()));
        if (!CollectionUtils.isEmpty(action.getActionExamples())) {
            //再保存所有的actionExamples
            List<ActionExampleEntity> actionExamples = new ArrayList<>();
            for (String actionExampleImage : action.getActionExamples()) {
                ActionExampleEntity actionExample = new ActionExampleEntity();
                actionExample.setActionId(action.getActionId());
                actionExample.setActionExampleImage(actionExampleImage);
                actionExamples.add(actionExample);
            }
            actionExampleService.saveBatch(actionExamples);
        }
        //先删除掉所有的actionEquipments
        actionEquipmentDao.delete(new QueryWrapper<ActionEquipmentEntity>().eq("action_id", action.getActionId()));
        if (!CollectionUtils.isEmpty(action.getEquipments())) {
            //再保存所有的actionEquipments
            List<ActionEquipmentEntity> actionEquipments = new ArrayList<>();
            for (EquipmentEntity equipment : action.getEquipments()) {
                ActionEquipmentEntity actionEquipment = new ActionEquipmentEntity();
                actionEquipment.setActionId(action.getActionId());
                actionEquipment.setEquipmentId(equipment.getEquipmentId());
                actionEquipments.add(actionEquipment);
            }
            actionEquipmentService.saveBatch(actionEquipments);
        }
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        ActionEntity temp = actionDao.selectById(id);
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            //要删除的内容不存在
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = actionDao.deleteByDeleteFlag(id);
        if (count == 0) {
            //删除失败
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        return true;
    }

}