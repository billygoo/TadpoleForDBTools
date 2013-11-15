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
package com.hangum.tadpole.editor.core.widgets.editor.json;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hangum.tadpole.editor.core.utils.TadpoleEditorUtils;
import com.hangum.tadpole.editor.core.widgets.editor.json.internal.JsonBrowserFunctionService;

/**
 * code editor
 * 
 * @author hangum
 * 
 */
public class JsonTadpoleEditor extends Composite {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(JsonTadpoleEditor.class);

	/** editor url resource */
	private static final String EDITOR_URL = "orion/tadpole/editor/jsonEmbeddededitor.html";

	/** 초기설정 텍스트 */
	private String initContent;

	/**
	 * 초기 content assist
	 */
	private String initAssist;

	/**
	 * 에디터를 보여줄 browser
	 */
	private Browser browserOrionEditor;

	/**
	 * browser.browserFunction의 서비스 헨들러
	 */
	private JsonBrowserFunctionService editorService;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 * @param initContent
	 * @param initAssist
	 */
	public JsonTadpoleEditor(Composite parent, int style, String initContent, String initAssist) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 1;
		gridLayout.horizontalSpacing = 1;
		gridLayout.marginHeight = 1;
		gridLayout.marginWidth = 1;
		setLayout(gridLayout);
		setBackgroundMode(SWT.INHERIT_FORCE);

		this.initContent = initContent;
		this.initAssist = initAssist;

		Composite compositeServerStatus = new Composite(this, SWT.NONE);
		compositeServerStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_compositeServerStatus = new GridLayout(1, false);
		gl_compositeServerStatus.verticalSpacing = 1;
		gl_compositeServerStatus.horizontalSpacing = 1;
		gl_compositeServerStatus.marginHeight = 1;
		gl_compositeServerStatus.marginWidth = 1;
		compositeServerStatus.setLayout(gl_compositeServerStatus);

		browserOrionEditor = new Browser(compositeServerStatus, SWT.NONE);
		browserOrionEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addBrowserHandler();
	}

	/**
	 * browser handler
	 */
	private void addBrowserHandler() {
		browserOrionEditor.setUrl(EDITOR_URL);

		registerBrowserFunctions();

		browserOrionEditor.addProgressListener(new ProgressListener() {
			public void completed(ProgressEvent event) {
				try {
					browserEvaluate("getEditor();");
				} catch (Exception e) {
					logger.error("javascript execute exception", e);
				}

				String callCommand = TadpoleEditorUtils.makeCommand("test.json", getInitContent(), getInitAssist());
				try {
					browserEvaluate(callCommand);
				} catch (Exception e) {
					logger.error("javascript execut exception", e);
				}
			}

			public void changed(ProgressEvent event) {
			}
		});
	}

	/**
	 * browser function call
	 * 
	 * @param command
	 *            brower command
	 */
	public Object browserEvaluate(String command) throws Exception {
		return browserOrionEditor.evaluate(command);
	}

	/**
	 * edit에 초기 데이터를 설정합니다.
	 * 
	 * @return
	 */
	public String getInitContent() {
		checkWidget();

		return initContent;
	}

	/**
	 * initialize assist
	 * 
	 * @return
	 */
	public String getInitAssist() {
		checkWidget();
		return initAssist;
	}

	/**
	 * get editor text
	 * 
	 * @return
	 */
	public String getText() {
		checkWidget();
		try {
			Object obj = browserEvaluate(JsonBrowserFunctionService.JAVA_SCRIPT_SAVE_FUNCTION);
			return obj.toString();
		} catch (Exception e) {
			logger.error("getText error", e);

			return "";
		}
	}

	/**
	 * set editor text
	 * 
	 * @param text
	 */
	public void setText(String text) {
		checkWidget();
		try {
			this.initContent = text;
			browserEvaluate(JsonBrowserFunctionService.JAVA_SCRIPT_RE_NEW_TEXT);
		} catch (Exception e) {

			logger.error("setText TadpoleEditor error" + e.getMessage() + "\r\n[start]" + this.initContent + "\r\n[end]");
		}
	}

	/**
	 * register browser function
	 */
	private void registerBrowserFunctions() {
		editorService = new JsonBrowserFunctionService(browserOrionEditor, JsonBrowserFunctionService.EDITOR_SERVICE_HANDLER, this);
	}

	/**
	 * unregister browser function
	 */
	private void unregisterBrowserFunctions() {
		if (editorService != null && editorService instanceof BrowserFunction) {
			editorService.dispose();
		}
	}

	@Override
	public void dispose() {
		unregisterBrowserFunctions();

		super.dispose();
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * set file name ps) Important thing, file name extension.
	 * 
	 * @return
	 */
	public Object getInputFileName() {
		return "initializeFile.js";// + langType.toString().toLowerCase();
	}

	/**
	 * append text
	 * 
	 * @return
	 */
	public String getAppendQueryText() {
		return "append text";
	}
}
