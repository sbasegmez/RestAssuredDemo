package com.developi.wink.demo.data;

import java.util.Collection;
import java.util.Iterator;

import lotus.domino.Base;

public class DominoUtils {

	/**
	 * Recycles a domino object instance.<br><br>
	 * 
	 * This is a smart function, you might provide list or array of values instead of a scalar one.
	 * 
	 * @param obj to recycle 
	 */
	public static void recycleObject(Object obj) {
		if (obj != null) {
			if (obj instanceof Base) {
				Base dominoObject = (Base) obj;
				try {
					dominoObject.recycle();
				} catch (Exception e) {
				}
			} else if (obj instanceof Collection) {
				Iterator<?> i = ((Collection<?>) obj).iterator();
				while (i.hasNext()) {
					Object colObj = i.next();
					recycleObject(colObj);
				}
			} else if (obj.getClass().isArray()) {
				try {
					Object[] objs = (Object[]) obj;
					for (Object ao : objs) {
						recycleObject(ao);
					}
				} catch (Throwable t) {
					// who cares?
				}
			}
		}
	}

	/**
	 * 	 recycles multiple domino objects (thx Nathan T. Freeman)
	 *		
	 * @param objs
	 */
	public static void recycleObjects(Object... objs) {
		for ( Object obj : objs ) 
			recycleObject(obj);
	}
	
}
