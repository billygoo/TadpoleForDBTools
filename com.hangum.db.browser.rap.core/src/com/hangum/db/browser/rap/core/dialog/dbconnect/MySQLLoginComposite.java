/*******************************************************************************
 * Copyright (c) 2012 Cho Hyun Jong.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Cho Hyun Jong - initial API and implementation
 ******************************************************************************/
package com.hangum.db.browser.rap.core.dialog.dbconnect;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.hangum.db.browser.rap.core.Activator;
import com.hangum.db.browser.rap.core.Messages;
import com.hangum.db.commons.sql.define.DBDefine;
import com.hangum.db.dao.system.UserDBDAO;
import com.hangum.db.exception.dialog.ExceptionDetailsErrorDialog;
import com.hangum.db.session.manager.SessionManager;
import com.hangum.db.system.TadpoleSystem_UserDBQuery;
import com.hangum.db.util.ApplicationArgumentUtils;

/**
 * mysql login composite
 * 
 * @author hangum
 *
 */
public class MySQLLoginComposite extends AbstractLoginComposite {
	private static final Logger logger = Logger.getLogger(MySQLLoginComposite.class);
	
	protected Combo comboGroup;
	protected Text textDisplayName;
	
	protected Text textHost;
	protected Text textUser;
	protected Text textPassword;
	protected Text textDatabase;
	protected Text textPort;
	protected Combo comboLocale;
	
	protected Button btnSavePreference;
	
	public MySQLLoginComposite(DBDefine selectDB, Composite parent, int style, List<String> listGroupName, String selGroupName, UserDBDAO userDB) {
		super(selectDB, parent, style, listGroupName, selGroupName, userDB);
		setText(selectDB.getDBToString());
	}

	@Override
	public void crateComposite() {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 3;
		gridLayout.horizontalSpacing = 3;
		gridLayout.marginHeight = 3;
		gridLayout.marginWidth = 3;
		setLayout(gridLayout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite compositeBody = new Composite(this, SWT.NONE);
		compositeBody.setLayout(new GridLayout(2, false));
		compositeBody.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		Label lblGroupName = new Label(compositeBody, SWT.NONE);
		lblGroupName.setText(Messages.MySQLLoginComposite_lblGroupName_text);
		comboGroup = new Combo(compositeBody, SWT.NONE);
		comboGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		for (String strGroup : listGroupName) comboGroup.add(strGroup);
		
		Label lblNewLabel_1 = new Label(compositeBody, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText(Messages.DBLoginDialog_lblNewLabel_1_text);
		
		textDisplayName = new Text(compositeBody, SWT.BORDER);
		textDisplayName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		new Label(compositeBody, SWT.NONE);		
		new Label(compositeBody, SWT.NONE);
		
		Label lblHost = new Label(compositeBody, SWT.NONE);
		lblHost.setText(Messages.DBLoginDialog_1);
		
		textHost = new Text(compositeBody, SWT.BORDER);
		textHost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabelPort = new Label(compositeBody, SWT.NONE);
		lblNewLabelPort.setText(Messages.DBLoginDialog_5);
		
		textPort = new Text(compositeBody, SWT.BORDER);
		textPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabelDatabase = new Label(compositeBody, SWT.NONE);
		lblNewLabelDatabase.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		lblNewLabelDatabase.setText(Messages.DBLoginDialog_4);
		
		textDatabase = new Text(compositeBody, SWT.BORDER);
		textDatabase.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));		
		
		Label lblUser = new Label(compositeBody, SWT.NONE);
		lblUser.setText(Messages.DBLoginDialog_2);
		
		textUser = new Text(compositeBody, SWT.BORDER);
		textUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPassword = new Label(compositeBody, SWT.NONE);
		lblPassword.setText(Messages.DBLoginDialog_3);
		
		textPassword = new Text(compositeBody, SWT.BORDER | SWT.PASSWORD);
		textPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblLocale = new Label(compositeBody, SWT.NONE);
		lblLocale.setText(Messages.MySQLLoginComposite_lblLocale_text);
		
		comboLocale = new Combo(compositeBody, SWT.NONE);
		comboLocale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if(selectDB == DBDefine.ORACLE_DEFAULT) {

			comboLocale.setVisibleItemCount(8);
			
			comboLocale.add("");
			
			comboLocale.add("ko");
			comboLocale.add("ja");
			comboLocale.add("zh");
			comboLocale.add("de");
			comboLocale.add("fr");
			comboLocale.add("it");
			comboLocale.add("en");
			comboLocale.select(0);
		} else if(selectDB == DBDefine.MYSQL_DEFAULT) {
			// http://dev.mysql.com/doc/refman//5.5/en/charset-charsets.html
			
			comboLocale.setVisibleItemCount(12);
			
			comboLocale.add("");
			comboLocale.add("armscii8 | ARMSCII-8 Armenian");
			comboLocale.add("ascii      | US ASCII");
			comboLocale.add("big5      | Big5 Traditional Chinese");
			comboLocale.add("binary    | Binary pseudo charset");
			comboLocale.add("cp850    | DOS West European");
			comboLocale.add("cp852    | DOS Central European");
			comboLocale.add("cp866    | DOS Russian");
			comboLocale.add("cp932    | SJIS for Windows Japanese");
			comboLocale.add("cp1250   | Windows Central European");
			comboLocale.add("cp1251   | Windows Cyrillic");
			comboLocale.add("cp1256   | Windows Arabic");
			comboLocale.add("cp1257   | Windows Baltic");
			comboLocale.add("dec8      | DEC West European");
			comboLocale.add("eucjpms  | UJIS for Windows Japanese");
			comboLocale.add("euckr     | EUC-KR Korean");
			comboLocale.add("gb2312   | GB2312 Simplified Chinese");
			comboLocale.add("gbk       | GBK Simplified Chinese");
			comboLocale.add("geostd8  | GEOSTD8 Georgian");
			comboLocale.add("greek     | ISO 8859-7 Greek");
			comboLocale.add("hebrew   | ISO 8859-8 Hebrew");
			comboLocale.add("hp8       | HP West European");
			comboLocale.add("keybcs2  | DOS Kamenicky Czech-Slovak");
			comboLocale.add("koi8r      | KOI8-R Relcom Russian");
			comboLocale.add("koi8u     | KOI8-U Ukrainian");
			comboLocale.add("latin1    | cp1252 West European");
			comboLocale.add("latin2    | ISO 8859-2 Central European");
			comboLocale.add("latin5    | ISO 8859-9 Turkish");
			comboLocale.add("latin7    | ISO 8859-13 Baltic");
			comboLocale.add("macce   | Mac Central European");
			comboLocale.add("macroman | Mac West European");
			comboLocale.add("sjis       | Shift-JIS Japanese");
			comboLocale.add("swe7    | 7bit Swedish");
			comboLocale.add("ucs2     | UCS-2 Unicode");
			comboLocale.add("tis620   | TIS620 Thai");
			comboLocale.add("ujis       | EUC-JP Japanese");
			comboLocale.add("utf8      | UTF-8 Unicode");
			comboLocale.add("utf8mb4 | UTF-8 Unicode");
			comboLocale.add("utf16     | UTF-16 Unicode");
			comboLocale.add("utf32     | UTF-32 Unicode");

			comboLocale.select(0);
		}
		
		Button btnPing = new Button(compositeBody, SWT.NONE);
		btnPing.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String host 	= StringUtils.trimToEmpty(textHost.getText());
				String port 	= StringUtils.trimToEmpty(textPort.getText());
				
				if("".equals(host) || "".equals(port)) { //$NON-NLS-1$ //$NON-NLS-2$
					MessageDialog.openError(null, Messages.DBLoginDialog_10, Messages.DBLoginDialog_11);
					return;
				}
				
				try {
					if(isPing(host, port)) {
						MessageDialog.openInformation(null, Messages.DBLoginDialog_12, Messages.DBLoginDialog_13);
					} else {
						MessageDialog.openError(null, Messages.DBLoginDialog_14, Messages.DBLoginDialog_15);
					}
				} catch(NumberFormatException nfe) {
					MessageDialog.openError(null, Messages.MySQLLoginComposite_3, Messages.MySQLLoginComposite_4);
				}
			}
		});
		btnPing.setText(Messages.DBLoginDialog_btnPing_text);
		
		btnSavePreference = new Button(compositeBody, SWT.CHECK);
		btnSavePreference.setText(Messages.MySQLLoginComposite_btnSavePreference_text);
		btnSavePreference.setSelection(true);

		init();
	}
	
	@Override
	public void init() {
		
		if(oldUserDB != null) {
			
			selGroupName = oldUserDB.getGroup_name();
			textDisplayName.setText(oldUserDB.getDisplay_name());
			
			textHost.setText(oldUserDB.getHost());
			textUser.setText(oldUserDB.getUsers());
			textPassword.setText(oldUserDB.getPasswd());
			textDatabase.setText(oldUserDB.getDb());
			textPort.setText(oldUserDB.getPort());
		} else if(ApplicationArgumentUtils.isTestMode()) {
			
			textDisplayName.setText(Messages.DBLoginDialog_21);
			
			textHost.setText(Messages.DBLoginDialog_16);
			textUser.setText(Messages.DBLoginDialog_17);
			textPassword.setText(Messages.DBLoginDialog_18);
			textDatabase.setText(Messages.DBLoginDialog_19);
			textPort.setText(Messages.DBLoginDialog_20);			
		}
		
		if(comboGroup.getItems().length == 0) {
			comboGroup.add(strOtherGroupName);
			comboGroup.select(0);
		} else {
			if("".equals(selGroupName)) {
				comboGroup.setText(strOtherGroupName);
			} else {
				// 콤보 선택 
				for(int i=0; i<comboGroup.getItemCount(); i++) {
					if(selGroupName.equals(comboGroup.getItem(i))) comboGroup.select(i);
				}
			}
		}
	}

	@Override
	public boolean connection() {
		if(!isValidate()) return false;

		String dbUrl = "";
		if(comboLocale.getText().trim().equals("")) {
			dbUrl = String.format(
					DBDefine.MYSQL_DEFAULT.getDB_URL_INFO(), 
					textHost.getText(), textPort.getText(), textDatabase.getText());
		} else {
			
			String selectLocale = StringUtils.substringBefore(comboLocale.getText(), "|");			
			
			dbUrl = String.format(
					DBDefine.MYSQL_DEFAULT.getDB_URL_INFO(), 
					textHost.getText(), textPort.getText(), textDatabase.getText() + "?Unicode=true&characterEncoding=" + selectLocale.trim());
		}
		
		userDB = new UserDBDAO();
		userDB.setTypes(DBDefine.MYSQL_DEFAULT.getDBToString());
		userDB.setUrl(dbUrl);
		userDB.setDb(textDatabase.getText());
		userDB.setGroup_name(comboGroup.getText().trim());
		userDB.setDisplay_name(textDisplayName.getText());
		userDB.setHost(textHost.getText());
		userDB.setPasswd(textPassword.getText());
		userDB.setPort(textPort.getText());
		userDB.setLocale(comboLocale.getText());
		userDB.setUsers(textUser.getText());
		
		// 기존 데이터 업데이트
		if(oldUserDB != null) {
			if(!MessageDialog.openConfirm(null, "Confirm", Messages.SQLiteLoginComposite_13)) return false; //$NON-NLS-1$
			
			if(!checkDatabase(userDB)) return false;
			
			try {
				TadpoleSystem_UserDBQuery.updateUserDB(userDB, oldUserDB, SessionManager.getSeq());
			} catch (Exception e) {
				logger.error(Messages.SQLiteLoginComposite_8, e);
				Status errStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e); //$NON-NLS-1$
				ExceptionDetailsErrorDialog.openError(getShell(), "Error", Messages.SQLiteLoginComposite_5, errStatus); //$NON-NLS-1$
				
				return false;
			}
			
			return true;
		// 신규 데이터 저장.
		} else {
			// 이미 연결한 것인지 검사한다.
			if(!connectValidate(userDB)) return false;
			
			// preference에 save합니다.
			if(btnSavePreference.getSelection())
				try {
					TadpoleSystem_UserDBQuery.newUserDB(userDB, SessionManager.getSeq());
				} catch (Exception e) {
					logger.error(Messages.MySQLLoginComposite_0, e);
					Status errStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e); //$NON-NLS-1$
					ExceptionDetailsErrorDialog.openError(getShell(), "Error", Messages.MySQLLoginComposite_2, errStatus); //$NON-NLS-1$
				}
			
			return true;
		}
	}
	
	/**
	 * 화면에 값이 올바른지 검사합니다.
	 * 
	 * @return
	 */
	public boolean isValidate() {
		if(!message(comboGroup, "Group")) return false;
		if(!message(textHost, "Host")) return false; //$NON-NLS-1$
		if(!message(textPort, "Port")) return false; //$NON-NLS-1$
		if(!message(textDatabase, "Database")) return false; //$NON-NLS-1$
		if(!message(textUser, "User")) return false; //$NON-NLS-1$
//		if(!message(textPassword, "Password")) return false; //$NON-NLS-1$
		if(!message(textDisplayName, "Display Name")) return false; //$NON-NLS-1$
		
		String host 	= StringUtils.trimToEmpty(textHost.getText());
		String port 	= StringUtils.trimToEmpty(textPort.getText());

		try {
			if(!isPing(host, port)) {
				MessageDialog.openError(null, Messages.DBLoginDialog_14, Messages.MySQLLoginComposite_8);
				return false;
			}
		} catch(NumberFormatException nfe) {
			MessageDialog.openError(null, Messages.MySQLLoginComposite_3, Messages.MySQLLoginComposite_4);
			return false;
		}
		
		return true;
	}
	
	/**
	 * text message
	 * 
	 * @param text
	 * @param msg
	 * @return
	 */
	protected boolean message(Text text, String msg) {
		if("".equals(StringUtils.trimToEmpty(text.getText()))) { //$NON-NLS-1$
			MessageDialog.openError(null, Messages.DBLoginDialog_10, msg + Messages.MySQLLoginComposite_10);
			text.setFocus();
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * combo message
	 * 
	 * @param text
	 * @param msg
	 * @return
	 */
	protected boolean message(Combo text, String msg) {
		if("".equals(StringUtils.trimToEmpty(text.getText()))) { //$NON-NLS-1$
			MessageDialog.openError(null, Messages.DBLoginDialog_10, msg + Messages.MySQLLoginComposite_10);
			text.setFocus();
			
			return false;
		}
		
		return true;
	}
	
}
