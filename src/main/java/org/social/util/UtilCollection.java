package org.social.util;

import java.util.Collection;
import java.util.List;

public class UtilCollection {

	public static <T> List<T> checkList(Object object, Class<T> type) {
		checkCollectionContainment(object, List.class, type);
		return checkList(object);
	}

	public static <C extends Collection<?>> void checkCollectionContainment(Object object, Class<C> clz, Class<?> type) {
		if (object != null) {
			if (!(clz.isInstance(object)))
				throw new ClassCastException("Not a " + clz.getName());
			int i = 0;
			for (Object value : (Collection<?>) object) {
				if (value != null && !type.isInstance(value)) {
					throw new IllegalArgumentException("Value(" + i + "), with value(" + value + ") is not a "
							+ type.getName());
				}
				i++;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> checkList(Object object) {
		return (List<T>) checkCollectionCast(object, List.class);
	}

	private static <C extends Collection<?>> C checkCollectionCast(Object object, Class<C> clz) {
		return clz.cast(object);
	}
}
