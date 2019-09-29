cd ~/xprojects/apps/dbclient/install/izpack/default
~/bin/IzPack/bin/compile install.xml -o dbclient-0.4-setup
cd ~/xprojects/apps/dbclient/install/izpack/thin
~/bin/IzPack/bin/compile install.xml -o dbclient-min-0.4-setup 
cp ~/xprojects/apps/dbclient/install/izpack/default/dbclient-0.4-setup.jar ~/xprojects/apps/dbclient/www/release/0.4
cp ~/xprojects/apps/dbclient/install/izpack/thin/dbclient-min-0.4-setup.jar ~/xprojects/apps/dbclient/www/release/0.4    