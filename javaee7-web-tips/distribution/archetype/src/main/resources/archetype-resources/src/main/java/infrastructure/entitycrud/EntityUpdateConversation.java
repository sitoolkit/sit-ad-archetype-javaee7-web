#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.entitycrud;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

@Named
@ConversationScoped
public class EntityUpdateConversation implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Serializable entityId;

    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getEntityId() {
        return (T) entityId;
    }

    public <T extends Serializable> void setEntityId(T entityId) {
        this.entityId = entityId;
    }

}
