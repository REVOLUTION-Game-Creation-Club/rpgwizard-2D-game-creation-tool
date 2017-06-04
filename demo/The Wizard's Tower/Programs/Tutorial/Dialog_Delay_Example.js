/*
 * Copyright (c) 2017, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Example of how to set the dialog box graphics, display a message, and close the message 
 * after it has been visible for 5 seconds.
 */

// Game assets used in this program
var assets = {
    "images": [
        "mwin_small.png",
        "sword_profile_1_small.png",
    ]
};

// Load up the assets we need for this program
rpgcode.loadAssets(assets, function() {
   // Set the dialog box background image, and profile picture.
   rpgcode.setDialogGraphics("sword_profile_1_small.png", "mwin_small.png");

   // Show the message in the dialog box.
   rpgcode.showDialog("This message will last 5 seconds on screen!");

   // Set a timer that removes the message after 5 seconds and ends the program.
   rpgcode.delay(5000, function() {
      rpgcode.clearDialog();
      rpgcode.endProgram();
   });
});
