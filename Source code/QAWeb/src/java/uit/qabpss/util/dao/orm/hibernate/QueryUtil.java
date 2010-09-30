/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package uit.qabpss.util.dao.orm.hibernate;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.dialect.Dialect;
import uit.qabpss.util.Randomizer;
import uit.qabpss.util.log.Log;
import uit.qabpss.util.log.LogFactoryUtil;

/**
 * <a href="QueryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class QueryUtil {

	public static final int ALL_POS = -1;

	public static Iterator<?> iterate(
		Query query, int start, int end) {

		return iterate(query, start, end, true);
	}

	public static Iterator<?> iterate(
		Query query, int start, int end,
		boolean unmodifiable) {

		return list(query, start, end).iterator();
	}



	public static List<?> list(
		Query query, int start, int end) {

		if ((start == ALL_POS) && (end == ALL_POS)) {
			return query.list();
		}
		else {

				query.setMaxResults(end - start);
				query.setFirstResult(start);

				return query.list();
			
			
		}
	}

	public static List<?> randomList(
		Query query, Dialect dialect, int total, int num) {

		return randomList(query, dialect, total, num, true);
	}

	public static List<?> randomList(
		Query query, Dialect dialect, int total, int num,
		boolean unmodifiable) {

		if ((total == 0) || (num == 0)) {
			return new ArrayList<Object>();
		}

		if (num >= total) {
			return list(query, ALL_POS, ALL_POS);
		}

		int[] scrollIds = Randomizer.getInstance().nextInt(total, num);

		List<Object> list = new ArrayList<Object>();

		ScrollableResults sr = query.scroll();

		for (int i = 0; i < scrollIds.length; i++) {
			if (sr.scroll(scrollIds[i])) {
				Object obj = sr.get(0);

				list.add(obj);

				sr.first();
			}
		}

		
			return list;
		
	}

	
	private static Log _log = LogFactoryUtil.getLog(QueryUtil.class);

}