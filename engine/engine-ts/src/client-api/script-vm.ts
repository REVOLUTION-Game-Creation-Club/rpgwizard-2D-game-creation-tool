/*
 * Copyright (c) 2017, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

export class ScriptVM {

    private _cache: object;
    private _inScript: boolean;

    constructor() {
        this._cache = {};
    }

    get inScript(): boolean {
        return this._inScript;
    }

    public async open(file: string): Promise<any> {
        console.debug("opening game script file=[%s]", file);

        let script: any;
        if (this._cache[file]) {
            script = this._cache[file];
        } else {
            script = await import(file);
            this._cache[file] = script;
        }

        console.debug("returning game script file=[%s]", file);

        return script;
    }

    public async run(file: string, origin: object, pause: boolean = true) {
        console.debug("running game script file=[%s], origin=[%s]", file, origin);

        try {
            if (pause) {
                this._inScript = true;
            }
            const script: any = await this.open(file);
            await script.default(origin);
        } catch (err) {
            console.error("could not run script, err=[%s]", err);
        } finally {
            if (pause) {
                this._inScript = false;
            }
        }

        console.debug("finished game script file=[%s], origin=[%s]", file, origin);
    }

}
