package com.alpha.community.enums;
/**
 * 回复类型有2种
 * 1.对问题回复
 * 2.对评论回复
 * @author alpha
 *
 */
public enum CommentTypeEnum {
	QUESTION(1),
	COMMENT(2);
	
	private Integer type;

	private CommentTypeEnum(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}
	/**
	 * 对外提供一个静态方法，判断是否存在该type
	 * @param type
	 * @return
	 */
	public static boolean isExist(Integer type) {
		CommentTypeEnum[] commentTypeEnums = CommentTypeEnum.values();
		for (CommentTypeEnum commentTypeEnum : commentTypeEnums) {
			if(type == commentTypeEnum.getType()) {
				return true;
			}
		}
		return false;
	}
	
}
