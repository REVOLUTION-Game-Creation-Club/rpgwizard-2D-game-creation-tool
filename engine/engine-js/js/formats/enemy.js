/*
 * Copyright (c) 2017, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/* global Sprite */

Enemy.prototype = Object.create(Sprite.prototype);
Enemy.prototype.constructor = Enemy;

function Enemy(filename) {
    console.info("Loading Enemy filename=[%s]", filename);
    Sprite.call(this);

    // TODO: Make the changes here that chrome suggests.
    var req = new XMLHttpRequest();
    req.open("GET", filename, false);
    req.overrideMimeType("text/plain; charset=x-user-defined");
    req.send(null);

    var npc = JSON.parse(req.responseText);
    for (var property in npc) {
        this[property] = npc[property];
    }

    this.calculateCollisionPoints();
    this.calculateActivationPoints();
}

Enemy.prototype.hitOnCollision = function (hitData, entity) {
    this.checkCollisions(hitData[0], entity);
};

Enemy.prototype.hitOffCollision = function (hitData, entity) {
    // Not used yet.
};

Enemy.prototype.hitOnActivation = function (hitData, entity) {
    this.checkActivations(hitData[0], entity);
};

Enemy.prototype.hitOffActivation = function (hitData, entity) {
    // Not used yet.
};

Enemy.prototype.checkCollisions = function (collision, entity) {
    console.debug("Checking collisions for NPC name=[%s]", this.name);

    var object = collision.obj;
    switch (object.vectorType) {
        case "ENEMY":
        case "NPC":
            entity.x += collision.normal.x;
            entity.y += collision.normal.y;
            entity.resetHitChecks();
            break;
        case "SOLID":
            entity.x += collision.normal.x;
            entity.y += collision.normal.y;
            entity.resetHitChecks();
            break;
    }
};

Enemy.prototype.checkActivations = function (collisions, entity) {
    // Not used yet.
};