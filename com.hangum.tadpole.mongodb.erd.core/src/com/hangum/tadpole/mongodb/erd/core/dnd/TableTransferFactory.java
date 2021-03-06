/*******************************************************************************
 * Copyright (c) 2013 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
package com.hangum.tadpole.mongodb.erd.core.dnd;

import org.eclipse.gef.requests.CreationFactory;

import com.hangum.tadpole.mongodb.model.Table;

public class TableTransferFactory implements CreationFactory {
	private Table table;

	@Override
	public Object getNewObject() {
		return table;
	}

	@Override
	public Object getObjectType() {
		return Table.class;
	}

	public void setTable(Table table) {
		this.table = table;
	}

}
