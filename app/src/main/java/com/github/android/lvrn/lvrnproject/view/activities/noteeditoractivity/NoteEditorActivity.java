package com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TabHost;

import com.commonsware.cwac.anddown.AndDown;
import com.github.android.lvrn.lvrnproject.R;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



//TODO: temporary implementation.
public class NoteEditorActivity extends AppCompatActivity {
    private static final String EDITOR_TAB_ID = "Editor" , PREVIEW_TAB_ID = "Preview";

    @BindView(R.id.edit_text_editor) EditText mEditorEditText;

    @BindView(R.id.web_view_preview) WebView previewWebView;

    private AndDown andDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        ButterKnife.bind(this);
        mEditorEditText = (EditText) findViewById(R.id.edit_text_editor);
        andDown = new AndDown();
        initTabs();
    }

    private void initTabs() {
        TabHost tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setup();
        tabHost.setOnTabChangedListener(this::showPreview);
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(EDITOR_TAB_ID);
        tabSpec.setContent(R.id.tab_editor);
        tabSpec.setIndicator(EDITOR_TAB_ID);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec(PREVIEW_TAB_ID);
        tabSpec.setContent(R.id.tab_preview);
        tabSpec.setIndicator(PREVIEW_TAB_ID);
        tabHost.addTab(tabSpec);
    }

    private void showPreview(String tabId) {
        if (PREVIEW_TAB_ID.equals(tabId)) {
            String text = mEditorEditText.getText().toString();
            List<Extension> extensions = Arrays.asList(
                    TablesExtension.create(),
                    AutolinkExtension.create(),
                    StrikethroughExtension.create());

            Parser parser = Parser.builder()
                    .extensions(extensions)
                    .build();
            Node document = parser.parse(text);



            HtmlRenderer renderer = HtmlRenderer.builder()
                    .extensions(extensions)
                    .build();
            String textHtml = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"

            System.out.println(textHtml);

            previewWebView.loadData(textHtml, "text/html", "UTF-8");
        }
    }
}
