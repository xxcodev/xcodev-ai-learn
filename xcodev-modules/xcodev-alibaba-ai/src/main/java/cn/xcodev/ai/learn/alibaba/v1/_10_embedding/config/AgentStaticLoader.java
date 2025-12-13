package cn.xcodev.ai.learn.alibaba.v1._10_embedding.config;

import com.alibaba.cloud.ai.agent.studio.loader.AgentLoader;
import com.alibaba.cloud.ai.graph.agent.Agent;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 *
 * @author xcodev
 */
@Component
public class AgentStaticLoader implements AgentLoader {

    private Map<String, Agent> agents = new ConcurrentHashMap<>();

    public AgentStaticLoader() {
    }

    public AgentStaticLoader(Agent... agents){
        this.agents = stream(agents).collect(toUnmodifiableMap(Agent::name, identity()));
    }


    @Override
    @Nonnull
    public List<String> listAgents() {
        return agents.keySet().stream().toList();
    }

    @Override
    public Agent loadAgent(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Agent name cannot be null or empty");
        }

        Agent agent = agents.get(name);
        if (agent == null) {
            throw new NoSuchElementException("Agent not found: " + name);
        }

        return agent;
    }
}
