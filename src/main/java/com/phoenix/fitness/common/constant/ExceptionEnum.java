package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    NOT_FOUND(404, "请求路径不存在，请检查路径是否正确"),
    BAD_REQUEST(400, "错误的请求！"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误！"),

    DUPLICATE_KEY(1001, "数据库中已存在该记录"),
    AUTHORIZATION_FAILURE(1010, "没有权限，请联系管理员授权"),
    ITEM_QUERY_NOT_FOUND(1020, "该id对应的内容不存在"),
    ITEM_UPDATE_NOT_FOUND(1021, "该修改内容不存在"),
    ITEM_DELETE_NOT_FOUND(1022, "该删除内容不存在"),
    ITEM_SAVE_FAILURE(1031, "新增内容失败"),
    ITEM_UPDATE_FAILURE(1032, "修改内容失败"),
    ITEM_DELETE_FAILURE(1033, "删除内容失败"),
    FILE_UPLOAD_EMPTY(1101, "上传文件不能为空"),

    COACH_DIFFERENT_TARGET(2101, "不能给同一个教练下发相同的目标！"),
    TARGET_DONT_HAVE_COACH(2103, "该目标下面没有关联该教练"),

    ACTION_TYPE_NOT_FOUND(3101, "动作分类不存在，请先确认动作分类是否正确"),
    GYM_NOT_FOUND(3105, "健身房不存在，请先创建健身房"),
    PARTNER_NOT_FOUND(3107, "该合作伙伴不存在，请先确认合作伙伴是否正确"),
    COACH_NOT_FOUND(3108, "教练不存在，请先确认教练信息是否正确"),
    CUSTOMER_NOT_FOUND(3109, "顾客不存在，请先确认顾客信息是否正确"),
    VISITOR_NOT_FOUND(3115, "访客不存在，请先确认访客信息是否正确"),
    CONTRACT_NOT_FOUND(3120, "合同不存在，请先确认合同信息是否正确"),

    VIP_CARD_NOT_FOUND(3130, "会员卡不存在，请先确认会员卡是否正确"),

    CUSTOMER_OPENID_NOT_FOUND(3110, "顾客的openId为空，请先确认顾客信息是否正确"),
    CUSTOMER_PHYSICAL_NOT_FOUND(3120, "实体卡对应的顾客不存在"),
    TARGET_NOT_FOUND(3111, "目标不存在，请先确认目标是否正确"),
    TARGET_TYPE_NOT_FOUND(3112, "目标分类不存在，请先确认目标分类是否正确"),
    MEAL_TYPE_NOT_FOUND(3115, "餐食类型不存在，请先确认餐食类型是否正确"),
    HEALTHY_MEAL_NOT_FOUND(3117, "健康餐不存在，请先确认健康餐是否正确"),
    ORDER_NOT_FOUND_WITH_NUMBER(3120, "根据订单编号查询不到该订单信息"),
    CUSTOMER_PLAN_DAY_NOT_FOUND(3124, "无法查询到该顾客当天的训练"),
    TRAINING_PLAN_NOT_FOUND(3150, "训练计划不存在，请先确认训练计划是否正确"),

    NO_TIMES_TO_USE(3130, "该卡已经使用完！"),

    MOBILE_DUPLICATE(3302, "用户手机已存在，请尝试重新输入"),
    COACH_NOT_IN_GYM(3301, "该教练不在此健身房中"),
    USERNAME_EXISTS(3401, "账号名称已经存在"),
    OLD_PASSWORD_ERROR(3405, "旧密码与先前的密码不一致"),
    CLOCK_IN_EMPTY(3501, "暂无打卡记录，请先打卡"),
    COACH_EQUAL_BEFORE(3601, "当前顾客的教练为现在这个教练，无需更换"),
    CONTRACT_TYPE_ERROR(3701, "合同类型错误"),

    VIP_CARD_TYPE_ERROR(3201, "会员卡类型错误，必须为WEEK_CARD、MONTH_CARD、SEASON_CARD、HALF_YEAR_CARD、THREE_SEASON_CARD、YEAR_CARD中的一种"),
    TARGET_PERIOD_TYPE_ERROR(3203, "目标阶段类型错误，必须为DAT_TARGET、WEEK_TARGET、MONTH_TARGET、SPECIAL_TARGET中的一种"),
    CUSTOMER_SURE_STATUS_ERROR(3302, "顾客只能确认教练已完成的训练"),
    ACTION_SET_TYPE_ERROR(3406, "动作组类型错误，必须为COMMON_SET、SUPER_SET、REDUCE_SET中的一种"),
    ACTION_SET_ACTION_ERROR(3407, "动作组中动作不存在，请确认数据格式"),
    REDUCE_SET_NUMBER_IS_NULL(3408, "递减组数不能为空"),

    BASE64_FILE_TYPE_ERROR(3601, "base64图片类型不正确"),
    PHYSICAL_CARD_INDEX_ERROR(3610, "该实体卡已经被其他顾客绑定"),
    PHYSICAL_CARD_NUMBER_ERROR(3611, "该实体卡id已经被其他顾客绑定"),

    PHYSIQUE_TEST_DATA_NOT_FOUND(4041, "未能找到该用户的体测报告，请确认是否上传"),

    ORDER_ITEM_IS_NULL(5001, "订单子项为空"),
    ORDER_ITEM_TRAINING_PLAN_IS_NULL(5002, "订单子项训练计划id为空"),
    OrderItemTrainingPlanIsInvalid(5003, "订单子项训练计划无效"),
    OrderItemTrainingPlanPriceIsInvalid(5004, "订单子项训练计划价格无效"),
    OrderCoachIsInvalid(5005, "订单-教练无效或者教练单价无效"),
    OrderItemVipCardIsNull(5006, "订单子项会员卡id为空"),
    OrderItemVipCardIsInvalid(5007, "订单子项会员卡无效"),
    OrderItemTypeIsError(5008, "订单子项类型不对"),

    MachineOrderAlipayException(7003, "支付宝接口调用成功，但返回错误"),
    MachineOrderAliUnPay(7004, "支付宝订单状态错误"),

    FORM_VALID_ERROR(10001, "表单校验错误"),
    REQUEST_METHOD_TYPE_ERROR(10002, "请求方法错误"),
    REQUEST_BODY_EMPTY_ERROR(10003, "请求体为空or请求参数类型错误"),
    REQUEST_ARGUMENT_TYPE_ERROR(10004, "请求参数类型错误");
    private final int code;
    private final String msg;

}
