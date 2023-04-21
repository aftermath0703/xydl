package com.flrjcx.xypt.common.enums;


import com.flrjcx.xypt.common.constants.IResultCode;

/**
 * @author quanchao
 */
public enum ResultCodeEnum implements IResultCode {


    /**
     * 成功消息码
     */
    SUCCESS(999999, "成功"),

    /**
     * 失败消息码
     */
    FAIL(0, "失败"),

    /**
     * 登录消息码
     */
    ERR_CODE_LOGIN_USER_FAILED(100001, "用户未登录"),
    USER_LOGIN_CODE_IP_NOT_ACCESS_PWD_ERROR(100007, "登录客户端IP不在允许密码登录IP范围内！"),
    USER_LOGIN_CODE_UN_VALID_FROM_ERROR(100008, "账号还未到开始使用期限！"),
    USER_LOGIN_CODE_INVALID_ERROR(100009, "账号已过有效期！"),
    USER_LOGIN_CODE_PASSWORD_INVALID_ERROR(100010, "账号密码已过期！"),
    USER_NOT_EXIST_ERROR(100011, "用户不存在"),
    USER_LOGIN_PWD_ERROR_CODE(100012, "账号或登录密码错误!"),
    USER_LOGIN_OTHER_CODE(100013, "用户已在其他客户端登录!"),
    ERROR_CODE_NAME_REQUIRED(100014, "用户名必填"),
    ERROR_CODE_PASSWORD_ERROR(100015, "密码必填"),
    ERROR_CODE_TOKEN_ERROR(100016, "token必填"),
    ERROR_CODE_INTOKEN_ERROR(100017, "token无效"),
    USER_ID_EXSIT_ERROR(100019, "用户ID不能为空"),
    ERROR_CODE_VERIFICATION_REQUIRED(100020, "验证码必填"),
    ERROR_CODE_VERIFICATION_ERROR_CODE(100021, "验证码错误"),

    ERROR_CODE_NICKNAME_ERROR_CODE(100022, "昵称格式错误"),
    ERROR_CODE_PASSWORD_ERROR_CODE(100023, "密码格式错误"),
    ERROR_CODE_EMAIL_REQUIRED(100024, "邮箱必填"),
    ERROR_CODE_EMAIL_ERROR_CODE(100025, "邮箱格式错误"),
    ERROR_CODE_SEX_REQUIRED(100026, "性别必填"),
    ERROR_USER_IS_EXIST(100027, "用户已被注册"),
    ERROR_EMAIL_IS_EXIST(100028, "邮箱已被注册"),
    ERROR_CODE_EMAIL_VERIFICATION_ERROR_CODE(100029, "邮箱验证码错误"),
    ERROR_CODE_EMAIL_ERROR(100030, "邮箱不存在"),
    ERROR_CODE_FORGET_PASSWORD_FAIL(100031, "忘记密码失败"),
    ERROR_CODE_EXIST_EMAIL(100032, "邮箱已存在"),
    ERROR_CODE_EMAIL_UNKNOWN_ERROR(100032, "关于邮箱的操作发生未知错误"),
    ERROR_CODE_ACCOUNT_ERROR_CODE(100033, "账号格式错误"),
    ERROR_CODE_EXIST_ACCOUNT(100034, "账户已存在"),
    ERROR_CODE_ACCOUNT_UNKNOWN_ERROR(100032, "关于账户的操作发生未知错误"),


    /**
     * 接口转发服务编码
     */
    ERROR_CODE_HIK_API_URL_EMPTY(200000, "请求url不能为空！"),
    ERROR_CODE_HIK_API_FORWARD(200001, "接口转发错误！"),


    /**
     * 角色错误码
     */
    ERROR_ROLE_EMPTY_NAME(110002, "角色名称不能为空！"),
    ERROR_ROLE_INSERT(110004, "增加角色失败！"),
    ERROR_ROLE_DETAIL(110005, "获取角色信息失败！"),
    ERROR_ROLE_DELETE(110006, "删除角色失败！"),
    ERROR_ROLE_EMPTY_ID(110007, "角色ID不能为空！"),
    ERROR_ROLE_QUERY_LIST(110008, "获取角色列表信息失败！"),
    ERROR_ROLE_UPDATE(110009, "修改角色失败！"),

    ERROR_ROLE_TEMPLATE_NAME_EMPTY(110010, "角色模板不能为空！"),
    ERROR_ROLE_TEMPLATE_NAME_EXIST(110011, "角色模板名称已经存在！"),
    ERROR_ROLE_TEMPLATE_NAME_INSERT(110012, "增加角色模板失败！"),
    ERROR_ROLE_TEMPLATE_QUERY_LIST(110013, "获取角色模板列表信息失败！"),
    ERROR_ROLE_TEMPLATE_UPDATE(110014, "修改角色模板列表信息失败！"),
    ERROR_ROLE_TEMPLATE_DELETE(110015, "删除角色模板列表信息失败！"),
    ERROR_ROLE_QUERY_STATUS_LIST(110016, "获取指定状态角色列表信息失败！"),
    ERROR_ROLE_QUERY_REGISTER_TIME_LIST(110017, "获取指定注册时间范围的角色列表信息失败！"),

    /**
     * 校验错误
     */
    ERROR_SENSITIVE_TEXT(120000, "文本含有敏感信息"),

    /**
     * 日志信息
     */
    ERROR_OPERATION_LOG_ADD(130000, "增加日志失败！"),
    ERROR_OPERATION_LOG_QUERY_PAGE(130001, "获取日志列表信息失败！"),
    ERROR_OPERATION_LOG_EVENT_ADD(130002, "增加日志事件失败！"),
    ERROR_OPERATION_LOG_EVENT_GET(130003, "获取日志事件失败！"),
    ERROR_OPERATION_LOG_EVENT_UPDATE(130004, "修改日志事件失败！"),
    ERROR_OPERATION_LOG_EVENT_DELETE(130005, "删除日志事件失败！"),
    ERROR_OPERATION_LOG_EVENT_QUERY(130006, "分页获取日志事件失败！"),
    ERROR_API_LOG_ADD(130007, "增加api操作日志失败！"),
    @SuppressWarnings("unused")
    CODE_PARAM_ERROR(40003, "参数错误"),
    USER_EXSIT_ERROR(40004, "用户已经注册"),

    CODE_INVALID_PHONE(40006, "手机号码格式错误"),
    CODE_EXSIT_PHONE(40007, "手机已注册"),
    CODE_EXSIT_PHONEORIDCARD(40008, "手机号或身份证号已存在"),
    CODE_EXSIT_CATENAME(40009, "分组类别名称已存在"),
    CODE_EXSIT_GROUPNAME(40010, "分组名称已存在"),
    PHONE_NOT_EXSIT_ERROR(40301, "手机号码不能为空"),
    USER_TYPE_ERROR(40302, "用户类型错误"),
    CODE_FUNCTIONTYPE_ERROR(40303, "验证码用途不能为空"),
    CODE_CLIP_ALREAY_REQUEST(40900, "指定的开始时间和结束时间已经剪辑过"),
    CODE_CLIP_INTEVEL_OVER(40600, "指定的时间超过限制，不被接受"),
    CODE_CLIP_NOT_EXIST(40400, "指定的时间段无数据"),
    PHONE_DUPLICATE_ERROR(403010, "手机号码重复"),
    NAME_PASSWORD_ERROR(403011, "手机号或密码错误"),
    MOBILE_CITY_ERROR(403014, "注册未通过，该产品仅对{}本地号码开放，敬请谅解"),
    ROLES_NAME_ERROR(40024, "角色名称重复"),


    CODE_OLD_PASSWORD_ERR(40304, "旧密码不正确"),
    CODE_CHECK_PASSWORD_ERR(40305, "密码不正确"),
    VERIFICATIONCODE_ERROR(40306, "验证码不存在或已过期，请重新输入"),
    VERIFICATIONCODE_ERROR_EXPIRE(40308, "验证码不存在或已过期，请重新输入"),
    VERIFICATIONCODE_SEND_ERROR(40307, "验证码发送失败"),
    VERIFICATIONCODE_SEND_OVERFLOW(40307, "验证码发送操作次数"),
    IMG_VERIFY_ERROR(4037, "图形验证码发送失败"),
    EXIST_CF_PWD(40308, "密码已添加，您可以修改"),
    CODE_NO_AUTHORITY(40329, "权限不足"),
    CODE_FROM_TOKEN_NO_USER(40335, "token中,获取不到用户标识"),
    OPERATION_ERROR(40336, "此用户无权做此操作"),
    NOT_FOUND_DESCRIPTION(40344, "不能找到value,通过这个key"),
    CODE_SYSTEM_ERROR(50001, "服务器错误"),
    ERROR_CODE_USER_KEY_NOT_FOUND(102011, "用户key没有找到"),
    TOO_FREQUENT_LOGIN(50103, "登录过于频繁,请一分钟后重试"),

    TOO_FREQUENT_REQUEST(50104, "请求过于频繁"),
    USERNAME_OR_PASSWORD_ERROR(50105, "账号或密码错误,请重新输入"),
    CURRENT_USER_UNUSUAL(50106, "当前登录账号异常,请联系管理员"),
    CURRENT_ACCOUNT_IS_LOCKED(50107, "您的账号已被锁定,请十分钟后重试"),
    TOO_FREQUENT_WRONG_LOGIN(50108, "密码错误,您还可以输入##次"),
    NOT_LOGIN_CURRENT_DEVICE_IN_30_DAYS(50109, "您的账号30日内未在本设备登录,请输入验证码"),
    CURRENT_ACCOUNT_NOT_BIND_PHONE(50110, "您的账号未绑定手机号,请联系管理员"),

    /**
     * 文件
     */
    ERROR_UPLOADFILE_FAILED(508000, "上传文件失败！"),
    ERR_CODE_FILE_SIZE_FAILED(508001, "文件大小最大为2M"),
    ERR_CODE_FILE_FORMATTER_FAILED(508002, "文件格式不支持"),
    ERR_CODE_FILE_NULL_ERROR(508003, "文件不能为空"),
    ERR_CODE_FILE_NAME_NULL_ERROR(508004, "文件名不能为空"),
    ERR_CODE_FACE__NULL_ERROR(508005, "用户头像不能为空"),
    ERR_CODE_QINIU_ERR(508006, "七牛云异常"),
    ERR_CODE_MENU_NULL_ERROR(508007, "目录为空"),


    ERROR_CODE_50111(50111, "根据权限查询用户失败 "),
    ERROR_CODE_50000(50000, "微服务调用异常 "),
    ERROR_CODE_50200(50200, "根据组织机构ID查询组织机构信息失败"),
    ERROR_CODE_50202(50202, "用户ID为空"),

    ERROR_CODE_50226(50226, "该账号被禁用,请联系管理员处理"),
    ERROR_CODE_50227(50227, "用户账号已过期,暂时无法使用，请联系管理员处理"),

    ERROR_CODE_50318(50318, "获取token失败 "),

    ERROR_CODE_50319(50319, "查询字段信息失败"),
    ERROR_CODE_50320(50320, "查询日志信息失败"),

    ERROR_CODE_50321(50321, "查询告警统计信息失败"),
    ERROR_CODE_50322(50322, "查询资源统计信息失败"),
    ERROR_CODE_50323(50323, "统计设备实时状态失败"),
    ERROR_CODE_50324(50324, "统计存储信息失败"),

    ERROR_CODE_50325(50325, "登录失败"),
    ERROR_CODE_50326(50326, "查询用户权限失败"),
    ERROR_CODE_50327(50327, "查询用户信息失败"),
    ERROR_CODE_50328(50328, "修改密码失败 "),
    ERROR_CODE_50329(50329, "登出失败"),
    ERROR_CODE_50330(50330, "修改用户失败"),
    ERROR_CODE_50331(50331, "删除用户失败"),
    ERROR_CODE_50332(50332, "新增用户失败"),
    ERROR_CODE_50333(50333, "根据组织机构查询用户失败"),
    ERROR_CODE_50334(50334, "查询用户角色失败"),
    ERROR_CODE_50335(50335, "删除角色失败"),
    ERROR_CODE_50336(50336, "新建角色失败"),
    ERROR_CODE_50337(50337, "编辑角色失败"),
    ERROR_CODE_50338(50338, "查询角色列表失败"),
    ERROR_CODE_50339(50339, "查询角色权限列表失败"),
    ERROR_CODE_50340(50340, "查询菜单权限列表失败"),
    ERROR_CODE_50341(50341, "查询用户微调权限失败"),

    ERROR_CODE_50342(50342, "发送手机验证码失败"),
    ERROR_CODE_50343(50343, "检验手机验证码失败"),

    ERROR_CODE_50344(50344, "查询用户信息失败"),

    /*工作流*/
    ERROR_CODE_COMMENT_ADD_WORKFLOW(600000, "增加指令评论失败！"),
    ERROR_CODE_COMMENT_USER_WORKFLOW(600001, "评论人ID不能为空！"),
    ERROR_CODE_COMMENT_DELETE_PARAM_EMPTY_WORKFLOW(600002, "参数不能全部为空！"),
    ERROR_CODE_COMMENT_DELETE_WORKFLOW(600003, "删除评论信息失败！"),
    ERROR_CODE_COMMENT_QUERY_WORKFLOW(600004, "获取评论信息失败！"),
    ERROR_CODE_COMMENT_UPDATE_WORKFLOW(600005, "修改评论信息失败！"),

    ERROR_CODE_BBS_UPDATE_ERROR(60006, "修改论坛表信息失败(数据库原因 或 该帖子不存在)"),
    ERROR_CODE_BBS_PRAISE_INSERT_ERROR(60007, "帖子点赞表增加信息失败(数据库原因)"),
    ERROR_CODE_BBS_PRAISE_DELETE_ERROR(60008, "帖子点赞表删除信息失败(数据库原因 或 帖子id及用户id可能不存在)"),
    ERROR_CODE_BBS_NO_INSERT_ERROR(60009, "帖子点踩表增加信息失败(数据库原因)"),
    ERROR_CODE_BBS_NO_DELETE_ERROR(60010, "帖子点踩表删除信息失败(数据库原因 或 帖子id及用户id可能不存在)"),

    ERROR_CODE_COMMENT_UPDATE_ERROR(60010, "修改评论表信息失败(数据库原因 或者 评论id不存在)"),
    ERROR_CODE_COMMENT_PRAISE_INSERT_ERROR(60011, "评论点赞表增加信息失败(数据库原因)"),
    ERROR_CODE_COMMENT_PRAISE_DELETE_ERROR(60012, "评论点赞表删除信息失败(数据库原因 或者 评论id及用户id不存在)"),

    ERROR_CODE_BBS_EDIT_ERROR(60013, "编辑帖子失败"),
    ERROR_CODE_BBS_DEL_ERROR(60014, "删除帖子失败"),

    ERROR_FORM_TABLE_EXIST(61000, "表单名称已经存在！"),

    ERROR_FORM_TABLE_CREATE(61001, "创建表格失败！"),

    ERROR_FORM_TABLE_PAGE_QUERY(61002, "查询表单失败！"),

    ERROR_FORM_TABLE_UPDATE(61003, "修改表单失败！"),

    ERROR_FORM_TABLE_NAME_EXIST(61004, "表单名称已存在！"),

    ERROR_FORM_TABLE_ID_EMPTY(61005, "表单ID不能为空！"),

    ERROR_TABLE_TEMPLATE_DOWNLOAD(61006, "下载表单模板失败！"),

    ERROR_TABLE_DATA_ADD(61007, "增加表单数据失败！"),

    ERROR_TABLE_DATA_QUERY(61008, "查询表单数据失败！"),

    ERROR_EXCEL_IMPORT(61007, "excel数据解析失败！"),

    ERROR_DELETE_FORM_DATA(61008, "删除表单数据失败！"),

    ERROR_DELETE_FORM_UPDATE(61009, "修改表单数据失败！"),

    ERROR_DELETE_FORM_UPDATE_EMPTY(61010, "参数不正确,请检查参数是否存在！"),

    ERROR_EXCEL_EXPORT(610011, "excel导出失败！"),
    ERROR_CODE_USER_UPDATE_USER_INFO(610012, "更新用户信息失败"),
    ERROR_CODE_SEARCH_POST_EMPTY(610013, "查询帖子为空"),

    //错误码范围102001-103000
    ERROR_CODE_PARAM_ERROR(102001, "请求参数非法"),
    ERROR_CODE_USER_SYSTEM_ERROR(102002, "用户服务内部异常"),
    ERROR_CODE_USER_NOT_FOUND(102003, "用户名或密码错误!"),
    //ERROR_CODE_PASSWORD_ERROR(102004, "密码连续输错{total}次后，账户将被锁定{hour}小时。您还有{times}次尝试机会"),
    //飘柔注释了上面的代码，改为下面的提示，飘柔之后再恢复
    //ERROR_CODE_PASSWORD_ERROR(102004, "旧密码输入错误，请重试！"),
    ERROR_CODE_USER_EXPIRE(102005, "您的账号已过期，请联系管理员办理延长手续!"),
    ERROR_CODE_VERIFICATIONCODE_EXPIRE(102006, "验证码不存在或已过期，请重新输入"),
    ERROR_CODE_NAME_PASSWORD_IDENTIFYING_CDOE_REQUIRED(102007, "用户名，密码，验证码必填"),
    ERROR_CODE_NAME_PASSWORD_IDENTIFYING_REQUIRED(102007, "用户名，密码必填"),
    ERROR_CODE_NAME_PASSWORD_REQUIRED(102008, "用户名，密码必填"),

    ERROR_CODE_REAL_NAME_IS_EMPTY(800004, "真实姓名不能为空"),
    ERROR_CODE_REAL_NAME_CONTAINS_ILLEGAL_CHARACTERS(800005, "真实姓名不能包含特殊字符"),
    ERROR_CODE_REAL_NAME_INCONFORMITY(800006, "真实姓名必须是2到10位中文字符"),
    ERROR_CODE_ID_CARD_IS_EMPTY(800007, "身份证号不能为空"),
    ERROR_CODE_ID_CARD_INCONFORMITY(800008, "身份证号不符合"),
    ERROR_CODE_REAL_REGISTERED(800009, "已实名，无需重复"),

    ERROR_EMAIL_SEND_KEY_WRONG(900001, "密钥错误"),
    SUCCESS_RECHARGE(900001, "成功充值S币:"),
    SUCCESS_DEPOSIT(900001, "成功提现金额:"),
    ERROR_DEPOSIT(900001, "提现金额大于当前S币!"),
    ERROR_DEPOSIT_NULL(900001, "提现了个寂寞!"),
    ERROR_RECHARGE_NULL(900001, "充值了个寂寞!"),
    ERROR_RECHARGE_MAX(900001, "超出S币单次充值额度!"),
    ERROR_REWARD_NULL(900001, "打赏0元不是Ikun!"),
    ERROR_REWARD_MAX(900001, "你的口袋不足以打赏!"),
    ERROR_BAN_REASON_NULL(900001, "封禁原因不能为空!"),
    ERROR_IMPOWER_USER(900001, "授权用户失败!"),
    ERROR_CANCEL_IMPOWER_USER(900001, "取消授权用户失败!"),
    ERROR_SELECT_IMPOWER_USER(900001, "查询已授权用户失败!"),
    ERROR_BAN_USER(900001, "账户已被封禁!"),
    ERROR_BBS_CONTEXT_NULL(900001, "帖子内容不能为空!"),
    ERROR_BBS_TITLE_NULL(900001, "帖子标题不能为空!"),
    ERROR_BBS_DESCRIPTION_NULL(900001, "帖子文章描述为空!"),
    ERROR_BBS_COVERPIC_NULL(900001, "文章封面为空!"),
    ERROR_BBS_HOT_NULL(900001, "用户未发任何帖子!"),


    END(900001, "仅用于结尾,无任何作用请勿删除!");


    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
