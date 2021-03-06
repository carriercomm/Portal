package com.lyndir.lhunath.portal.webapp.page;

import static com.lyndir.lhunath.opal.system.util.ObjectUtils.ifNotNullElse;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.lyndir.lhunath.opal.system.util.ObjectUtils;
import com.lyndir.lhunath.opal.wayward.behavior.CSSClassAttributeAppender;
import com.lyndir.lhunath.opal.wayward.behavior.CSSStyleAttributeAppender;
import com.lyndir.lhunath.portal.webapp.PortalWebApplication;
import com.lyndir.lhunath.portal.webapp.model.PortalPageMeta;
import com.lyndir.lhunath.portal.webapp.model.StripItem;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.behavior.StringHeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.*;
import org.apache.wicket.util.template.JavaScriptTemplate;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * <h2>{@link PortalPage}<br> <sub>[in short] (TODO).</sub></h2>
 *
 * <p> <i>05 02, 2010</i> </p>
 *
 * @author lhunath
 */
public abstract class PortalPage extends WebPage {

    protected PortalPage() {

        add( new Label( "pageTitle", getPageTitle() ) );
        add(
                new ListView<StripItem>(
                        "strip", PortalWebApplication.get().getStripItems() ) {

                    @Override
                    protected void populateItem(final ListItem<StripItem> item) {

                        item.add(
                                new CSSStyleAttributeAppender(
                                        "background-image", new LoadableDetachableModel<String>() {
                                    @Override
                                    protected String load() {

                                        return String.format( "url('%s')", item.getModelObject().getIcon() );
                                    }
                                } ) );
                        item.add( new Label( "caption", item.getModelObject().getName() ) );
                        item.add( new Label( "description", item.getModelObject().getDescription() ) );
                        item.add( new ExternalLink( "link", item.getModelObject().getLink() ) );
                        item.add(
                                CSSClassAttributeAppender.of(
                                        new LoadableDetachableModel<String>() {
                                            @Nullable
                                            @Override
                                            protected String load() {

                                                boolean isActive = ObjectUtils.isEqual(
                                                        item.getModelObject(), PortalWebApplication.get().getActiveItem() );
                                                return isActive? "active": null;
                                            }
                                        } ) );
                    }
                } );
        add(
                new WebMarkupContainer( "logo" ).add(
                        new CSSStyleAttributeAppender(
                                "background-image", new LoadableDetachableModel<String>() {
                            @Override
                            protected String load() {

                                return String.format( "url('%s')", PortalWebApplication.get().getActiveItem().getLogo() );
                            }
                        } ) ) );
        add(
                new ListView<PortalPageMeta>( "menu", PortalWebApplication.get().getPortalPages() ) {

                    @Override
                    protected void populateItem(final ListItem<PortalPageMeta> item) {

                        item.add(
                                new Link<Void>( "link" ) {
                                    @Override
                                    protected void onInitialize() {

                                        super.onInitialize();

                                        add( new Label( "icon", item.getModelObject().getIcon() ) );
                                        add( new Label( "name", item.getModelObject().getName() ) );
                                    }

                                    @Override
                                    public void onClick() {

                                        throw new RestartResponseException( item.getModelObject().getPage() );
                                    }
                                } );
                        item.add(
                                new CSSClassAttributeAppender(
                                        item.getModelObject().getPage().isAssignableFrom( getPageClass() )? "active": "" ) );
                    }
                } );
        add(
                new Label(
                        "foot", new LoadableDetachableModel<String>() {
                    @Override
                    protected String load() {

                        return ifNotNullElse(
                                PortalWebApplication.get().getActiveItem().getFooter(), new Function<Model<String>, String>() {
                                    @Override
                                    public String apply(final Model<String> from) {

                                        return from.getObject();
                                    }
                                }, loadFooter() );
                    }
                } ).setEscapeModelStrings( false ) );
        add(
                new StringHeaderContributor(
                        new LoadableDetachableModel<String>() {
                            @Override
                            protected String load() {

                                return new JavaScriptTemplate( new PackagedTextTemplate( PortalPage.class, "trackPage.js" ) ).asString(
                                        ImmutableMap.<String, Object>builder() //
                                                .put( "googleAnalyticsID", getGoogleAnalyticsID() ) //
                                                .put( "pageView", getPageClass().getSimpleName() ).build() );
                            }
                        } ) );
    }

    @NotNull
    protected abstract String getGoogleAnalyticsID();

    @NotNull
    protected String loadFooter() {

        return "Copyright 2011, lhunath";
    }

    protected abstract IModel<String> getPageTitle();
}
