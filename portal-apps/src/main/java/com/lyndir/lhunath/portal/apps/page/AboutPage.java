/*
 *   Copyright 2009, Maarten Billemont
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.lyndir.lhunath.portal.apps.page;

import com.lyndir.lhunath.portal.apps.model.AppVersion;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


/**
 * <h2>{@link AboutPage}<br> <sub>[in short] (TODO).</sub></h2>
 *
 * <p> [description / usage]. </p>
 *
 * <p> <i>May 31, 2009</i> </p>
 *
 * @author lhunath
 */
public class AboutPage extends AppsPage {

    public AboutPage() {

        add( new ListView<AppVersion>( "versions", AppVersion.getAll() ) {

            @Override
            protected void populateItem(final ListItem<AppVersion> versionItem) {

                AppVersion version = versionItem.getModelObject();

                versionItem.add( new Label( "version", version.getFriendlyVersion() ) );
                versionItem.add( new ListView<String>( "changes", version.getChanges() ) {

                    @Override
                    protected void populateItem(final ListItem<String> versionChangeItem) {

                        versionChangeItem.add( new Label( "change", versionChangeItem.getModelObject() ) );
                    }
                } );
                versionItem.setVisible( !version.getChanges().isEmpty() );
            }
        } );
    }
}
