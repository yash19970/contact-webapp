/**
 * 
 */
package com.contact.contactwebapp.crud.enums;

/**
 * @author yashjain
 *
 */
public enum DBStatusEnum {

	INACTIVE("inactive", Byte.valueOf("0")), ACTIVE("active", Byte.valueOf("1")), TESTING("testing",
			Byte.valueOf("3")), RESUME_LATER("resume later", Byte.valueOf("4")), OMITTED("omitted", Byte.valueOf("5"));

	private byte value;
	private String desc;

	DBStatusEnum(String desc, byte value) {
		this.value = value;
		this.desc = desc;
	}

	public static DBStatusEnum getEnumByValue(byte value) {
		for (DBStatusEnum source : DBStatusEnum.values()) {
			if (source.getValue() == value) {
				return source;
			}
		}
		return null;
	}

	/**
	 * @return the id
	 */
	public byte getValue() {
		return value;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

}
