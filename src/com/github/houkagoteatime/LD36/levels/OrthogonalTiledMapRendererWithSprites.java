package com.github.houkagoteatime.LD36.levels;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.github.houkagoteatime.LD36.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {
    private List<Entity> entitys;
    private int drawSpritesAfterLayer = 1;

    public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
        super(map);
        entitys = new ArrayList<Entity>();
       
    }

    public void addEntity(Entity entity){
        entitys.add(entity);
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
        	renderTileLayer((TiledMapTileLayer)layer);
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                    currentLayer++;
                    if(currentLayer == drawSpritesAfterLayer){
                        for(Entity entity: entitys) {
                        	this.getBatch().draw(entity.getSprite(), entity.getPosition().x, entity.getPosition().y);
                        }
                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }
        endRender();
    }
}
