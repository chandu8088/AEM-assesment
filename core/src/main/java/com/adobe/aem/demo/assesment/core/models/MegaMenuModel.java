package com.adobe.aem.demo.assesment.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MegaMenuModel {

    @Inject
    private Page currentPage;

    @SlingObject
    ResourceResolver resourceResolver;

    @Self
    SlingHttpServletRequest request;

    private static final Logger LOGGER = LoggerFactory.getLogger(MegaMenuModel.class);

    public List<ChildPagesBeanModel> getChildrenRoot(){
        String path = currentPage.getPath();
        return retrieveChildren(path);

    }

    private List<ChildPagesBeanModel> retrieveChildren(String rootPath){
        try{
            List<ChildPagesBeanModel> childPages  = new ArrayList<>();
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            Page rootPage = Objects.requireNonNull(pageManager).getContainingPage(rootPath);
            Iterator<Page> childResource  =  rootPage.listChildren();
            while (childResource.hasNext()){
                Page child = childResource.next();
                if(!child.isHideInNav()){
                    ChildPagesBeanModel childPagesBeanModel = createPageItem(child);
                    childPages.add(childPagesBeanModel);
                }
            }
            return childPages;
        }
        catch (NullPointerException e){
            LOGGER.error("Exception while accessing child pages {}",e.getMessage());
            return Collections.emptyList();
        }
    }

    private ChildPagesBeanModel createPageItem(Page childPage){
        ChildPagesBeanModel childPagesBeanModel = new ChildPagesBeanModel();
        childPagesBeanModel.setName(childPage.getName());
        childPagesBeanModel.setPath(childPage.getPath());
        if(childPage.listChildren()!=null) {
            childPagesBeanModel.setChildPages(retrieveChildren(childPage.getPath()));
        }
        else
            childPagesBeanModel.setChildPages(null);
        return childPagesBeanModel;
    }

}
