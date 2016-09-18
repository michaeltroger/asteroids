/**
Copyright 2016 Michael Troger <https://michaeltroger.com>
This file is part of Asteroids Clone with LibGDX.

Asteroids Clone with LibGDX is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Asteroids Clone with LibGDX is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Asteroids Clone with LibGDX.  If not, see <http://www.gnu.org/licenses/>.
*/
package at.fhoe.mtd.igp.systems;

import at.fhoe.mtd.igp.components.*;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;
import messages.CollisionMsg;

/**
 * Created by Michael on 6/9/2015.
 */

public class OrbSystem extends IteratingSystem
      {


    private ComponentMapper<OrbCmp> orbMapper;
    //private ComponentMapper<ShipCmp> shipMapper;
      private ComponentMapper<PoseCmp> poseMapper;
    private Engine engine;
    private Vector2 shipPos;

    private static final Family family = Family.getFor(
            OrbCmp.class
    );


    public OrbSystem() {
        super(family);

        orbMapper = ComponentMapper.getFor(OrbCmp.class);
      //  shipMapper = ComponentMapper.getFor(ShipCmp.class);
        poseMapper = ComponentMapper.getFor(PoseCmp.class);  //virtueller konstruktor
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        OrbCmp orb = orbMapper.get(entity);
       // ShipCmp ship = shipMapper.get(entity);
        PoseCmp pose = poseMapper.get(entity);

        shipPos = ShipSystem.getShipPos();


        //pose.pos = shipPos.rotate(pose.angle++);
        //pose.pos = shipPos.scl((float)Math.PI);
        pose.pos.x = shipPos.x -= orb.distance*Math.cos(pose.angle);
        pose.pos.y = shipPos.y -= orb.distance*Math.sin(pose.angle);

        pose.angle = pose.angle-0.05f;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }




}