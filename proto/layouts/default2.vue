<template>
  <v-app :class="nanum ? 'nanum' : ''">
    <v-navigation-drawer
      app fixed class="elevation-1"
      v-model="drawer"
      :mini-variant="miniVariant"
      :clipped="clipped">

      <v-list-item class="logo" @click="nanum != nanum">
        <img class="logo-svg" src="~/assets/logo.svg" />

        <v-list-item-content py-10>
          <v-list-item-title class="til">
            Application
          </v-list-item-title>
          <v-list-item-subtitle class="sub">
            subtext
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

      <v-list nav flat expand dense>
        <v-list-item-group mandatory v-model="selected">
          <template v-for="(val, name) in items">
            <v-subheader class="text-uppercase" :key="name">
                {{ name }}
            </v-subheader>
            <v-list-item
              v-for="(item, i) in val"
              :key="i"
              :to="item.link"
              router
              exact
            >
              <v-list-item-action>
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title v-text="item.title" />
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-list-item-group>
      </v-list>

      <!--
      <template v-slot:append>
        <v-divider />
        <div class="pa-2">
          <v-btn text class="primary body-2 font-weight-bold" to="/home">
            <v-icon>mdi-cog</v-icon>
            <span class="ml-3 mr-10">
            System Admin
            </span>
            <v-icon>mdi-chevron-right</v-icon>
          </v-btn>
        </div>
      </template>
      -->
    </v-navigation-drawer>

    <v-app-bar :clipped-left="clipped" app fixed dense elevation-0>
      <v-spacer />

      <v-btn text>
        Platform Admin
      </v-btn>
    </v-app-bar>

    <v-content>
      <v-container fluid pa-0>
        <nuxt />
      </v-container>
    </v-content>

  </v-app>
</template>

<script>
export default {
  data () {
    return {
      nanum: true,
      clipped: false,
      drawer: true,
      fixed: false,
      items: {
        'System Config': [
          { title: "Clusters", icon: "mdi-home", link: "/master/clusters" },
          { title: "Projects", icon: "mdi-source-pull", link: "/master/proj" },
          { title: "Tools", icon: "mdi-book-variant", link: "/master/tools" },
          { title: "Users", icon: "mdi-book-variant", link: "/master/users" },
          { title: "Groups", icon: "mdi-book-variant", link: "/master/groups" },
          { title: "Roles", icon: "mdi-book-variant", link: "/master/roles" }
        ],
        'Platform Config': [
          { title: "Realms", icon: "mdi-account-key", link: "/platform/realms" },
          { title: "Menus", icon: "mdi-clipboard-account", link: "/platform/menus" },
          { title: "Notice", icon: "mdi-clipboard-account", link: "/platform/notice" },
          { title: "Preferences", icon: "mdi-clipboard-account", link: "/platform/pref" }
        ],
      },
      miniVariant: false,
      right: true,
      rightDrawer: false,
      title: 'Vuetify.js'
    }
  }
}
</script>
<style scoped>
/* .v-navigation-drawer { background: linear-gradient( to bottom, #0f4c81, #1173ce 1%, #0f4c81 50%); } */
/* .v-navigation-drawer { background: linear-gradient( to top left, #000, #0f4c81); } */

/*
.v-navigation-drawer .v-subheader { color: rgba(239, 239, 239, 0.3) !important; }
.v-navigation-drawer .v-list .v-list-item:not(.v-list-item--active) { color: rgba(239, 239, 239, 0.3) !important; }
.v-navigation-drawer .v-list .v-list-item:not(.v-list-item--active) .v-icon { color: rgba(239, 239, 239, 0.3) !important; }
*/

/*
.v-navigation-drawer .v-list-item-group .v-list-item--active { color: #efefef !important; }
.v-navigation-drawer .v-list-item-group .v-list-item--active .v-icon { color: #efefef !important; }
*/

.logo { height: 140px; }
/* .logo.v-list-item:not(.v-list-item--active) { background-color: rgba(24, 169, 204); } */
/* .logo.v-list-item:not(.v-list-item--active) { background-color: #7ae8ce; } */
/* .logo.v-list-item:not(.v-list-item--active) { background-color: rgb(24, 167, 204); } */
.logo.v-list-item:not(.v-list-item--active) { background-color: #0f4c81; }
.logo.v-list-item:not(.v-list-item--active) .til { color: #fff; font-weight: bold; }
.logo.v-list-item:not(.v-list-item--active) .sub { color: #fff; }

.v-navigation-drawer .v-divider { border-color: rgba(239, 239, 239, 0.3); padding-left: 10px; }

/* .v-content { background-color: #fafafa; }
.v-app-bar { background-color: #fafafa !important; box-shadow: none; } */
.v-content { background-color: #e7ecef; }
.v-app-bar { background-color: #e7ecef !important; box-shadow: none; }

.nanum { font-family: "Nanum Gothic", sans-serif !important; }

.logo .logo-svg { margin-right: 16px; width: 40px; height: 40px; }
</style>
