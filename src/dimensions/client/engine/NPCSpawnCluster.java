package dimensions.client.engine;

import java.security.SecureRandom;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import dimensions.client.engine.spriteinterfaces.NPC;

public class NPCSpawnCluster
{
	private final Class<NPC> npcClass;
	private final int centerX, centerY, centerZ;
	private final int spawnRadius, instances, reviveTime;
	private final BlockingQueue<NPC> queue;
	private long lastSpawned = System.currentTimeMillis();
	private final SecureRandom random = new SecureRandom();
	
	public NPCSpawnCluster(Class<NPC> npcClass, final int x, final int y, final int z, final int radius, final int reviveTime, final int instances) throws InstantiationException, IllegalAccessException
	{
		this.npcClass = npcClass;
		this.centerX = x;
		this.centerY = y;
		this.centerZ = z;
		
		this.spawnRadius = radius;
		this.reviveTime = reviveTime;
		this.instances = instances;
		queue = new ArrayBlockingQueue<NPC>(instances);
		while(queue.remainingCapacity() > 0)
			createNPC();
	}
	
	private void createNPC() throws InstantiationException, IllegalAccessException
	{
		final NPC npc = npcClass.newInstance();
		npc.setWorldX(centerX + random.nextInt(spawnRadius) - spawnRadius/2);
		npc.setWorldY(centerY + random.nextInt(spawnRadius) - spawnRadius/2);
		npc.setWorldZ(centerZ);
		queue.offer(npc);
	}
	
	public NPC getNextNPC() throws InstantiationException, IllegalAccessException
	{
		if(readyToFillQueue())
			createNPC();
		if(queue.isEmpty())
			return null;
		lastSpawned = System.currentTimeMillis();
		return queue.poll();
	}

	private boolean readyToFillQueue()
	{
		final int timeSinceLastSpawn = (int) ((System.currentTimeMillis() - lastSpawned)/1000);
		return queue.remainingCapacity() > 0 && timeSinceLastSpawn > reviveTime;
	}
	
}
