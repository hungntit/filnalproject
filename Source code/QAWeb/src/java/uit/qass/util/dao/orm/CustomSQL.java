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

package uit.qass.util.dao.orm;




import java.util.Map;

import uit.qass.util.OrderByComparator;
import uit.qass.util.StringPool;
import uit.qass.util.StringUtil;
import uit.qass.util.Validator;

/**
 * <a href="CustomSQL.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 *
 */
public class CustomSQL {


	public CustomSQL()  {
		
	}

	public String get(String id) {
		return _sqlPool.get(id);
	}


	public String[] keywords(String keywords) {
		return keywords(keywords, true);
	}

	public String[] keywords(String keywords, boolean lowerCase) {
		if (lowerCase) {
			keywords = keywords.toLowerCase();
		}

		keywords = keywords.trim();

		String[] keywordsArray = StringUtil.split(keywords, StringPool.SPACE);

		for (int i = 0; i < keywordsArray.length; i++) {
			String keyword = keywordsArray[i];

			keywordsArray[i] =
				StringPool.PERCENT + keyword + StringPool.PERCENT;
		}

		return keywordsArray;
	}

	public String[] keywords(String[] keywordsArray) {
		return keywords(keywordsArray, true);
	}

	public String[] keywords(String[] keywordsArray, boolean lowerCase) {
		if ((keywordsArray == null) || (keywordsArray.length == 0)) {
			keywordsArray = new String[] {null};
		}

		if (lowerCase) {
			for (int i = 0; i < keywordsArray.length; i++) {
				keywordsArray[i] = StringUtil.lowerCase(keywordsArray[i]);
			}
		}

		return keywordsArray;
	}

	public String replaceAndOperator(String sql, boolean andOperator) {
		String andOrConnector = "OR";
		String andOrNullCheck = "AND ? IS NOT NULL";

		if (andOperator) {
			andOrConnector = "AND";
			andOrNullCheck = "OR ? IS NULL";
		}

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$AND_OR_CONNECTOR$]", "[$AND_OR_NULL_CHECK$]"
			},
			new String[] {
				andOrConnector, andOrNullCheck
			});


		sql = replaceIsNull(sql);

		return sql;
	}

	public String replaceIsNull(String sql) {
		if (Validator.isNotNull(_functionIsNull)) {
			sql = StringUtil.replace(
				sql,
				new String[] {
					"? IS NULL", "? IS NOT NULL"
				},
				new String[] {
					_functionIsNull,
					_functionIsNotNull
				});
		}

		return sql;
	}

        /**
         *
         * @param fieldname
         * @param operator
         * @return Structure: (fieldname operator ? [$AND_OR_NULL_CHECK$])
         */
        public String createOperatorForField(String fieldname,String operator){
            if(operator.equalsIgnoreCase(StringPool.LIKE))
                return "("+fieldname+" "+operator+" ? [$AND_OR_NULL_CHECK$])";
            else
                return "("+fieldname+" "+operator+" ? )";
        }
	public String replaceKeywords(
		String sql, String field, String operator, boolean last,
		String[] values) {

		if (values.length == 0) {
			return sql;
		}

		StringBuilder oldSql = new StringBuilder();

		oldSql.append("(");
		oldSql.append(field);
		oldSql.append(" ");
		oldSql.append(operator);
		oldSql.append(" ? [$AND_OR_NULL_CHECK$])");

		if (!last) {
			oldSql.append(" [$AND_OR_CONNECTOR$]");
		}

		StringBuilder newSql = new StringBuilder();

		newSql.append("(");

		for (int i = 0; i < values.length; i++) {
			if (i > 0) {
				newSql.append(" AND ");
			}

			newSql.append("(");
			newSql.append(field);
			newSql.append(" ");
			newSql.append(operator);
			newSql.append(" ? [$AND_OR_NULL_CHECK$])");
		}

		newSql.append(")");

		if (!last) {
			newSql.append(" [$AND_OR_CONNECTOR$]");
		}

		return StringUtil.replace(sql, oldSql.toString(), newSql.toString());
	}

	public String removeOrderBy(String sql) {
		int pos = sql.indexOf(" ORDER BY ");

		if (pos != -1) {
			sql = sql.substring(0, pos);
		}

		return sql;
	}

	public String replaceOrderBy(String sql, OrderByComparator obc) {
		if (obc == null) {
			return sql;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(removeOrderBy(sql));
		sb.append(" ORDER BY ");
		sb.append(obc.getOrderBy());

		return sb.toString();
	}

	

	
	private String _functionIsNull;
	private String _functionIsNotNull;
	private Map<String, String> _sqlPool;

}