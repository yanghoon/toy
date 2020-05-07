<template>
  <v-app>
    <v-navigation-drawer
      v-model="drawer"
      :mini-variant="miniVariant"
      clipped
      fixed
      app>

      <v-list nav dense>
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
    </v-navigation-drawer>

    <v-app-bar clipped-left fixed app elevation-0 class="primary">
      <!-- <v-app-bar-nav-icon @click.stop="drawer = !drawer" /> -->

    <!--
      <v-btn icon @click.stop="miniVariant = !miniVariant">
        <v-icon>mdi-{{ `chevron-${miniVariant ? 'right' : 'left'}` }}</v-icon>
      </v-btn>
      <v-btn icon @click.stop="clipped = !clipped">
        <v-icon>mdi-application</v-icon>
      </v-btn>
      <v-btn icon @click.stop="fixed = !fixed">
        <v-icon>mdi-minus</v-icon>
      </v-btn>
    -->

      <!-- <v-toolbar-title v-text="title" /> -->
      <v-list-item-title class="til">
        Application
      </v-list-item-title>

      <v-spacer />
      <!--
      <v-btn icon @click.stop="rightDrawer = !rightDrawer">
        <v-icon>mdi-menu</v-icon>
      </v-btn>
      -->
      <v-btn text>
        Platform Admin
      </v-btn>
    </v-app-bar>

    <v-content>
      <v-container fluid pa-0>
        <nuxt />
      </v-container>
    </v-content>
    <v-navigation-drawer
      v-model="rightDrawer"
      :right="right"
      temporary
      fixed
    >
      <v-list>
        <v-list-item @click.native="right = !right">
          <v-list-item-action>
            <v-icon light>
              mdi-repeat
            </v-icon>
          </v-list-item-action>
          <v-list-item-title>Switch drawer (click me)</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <!--
    <v-footer :fixed="fixed" app>
      <span>&copy; {{ new Date().getFullYear() }}</span>
    </v-footer>
    -->
  </v-app>
</template>

<script>
export default {
  data () {
    return {
      clipped: false,
      drawer: true,
      fixed: false,
      items: {
        Applications: [
          { title: "Home", icon: "mdi-home", link: "/home" },
          { title: "CI/CD", icon: "mdi-source-pull" },
          { title: "Logging", icon: "mdi-book-variant" },
          { title: "Monitoring", icon: "mdi-book-variant" }
        ],
        'Project Config': [
          { title: "Authrity", icon: "mdi-account-key", link: "/iam" },
          { title: "Roles", icon: "mdi-clipboard-account", link: "" },
          { title: "Groups", icon: "mdi-clipboard-account", link: "" },
          { title: "Settings", icon: "mdi-clipboard-account", link: "" }
        ],
        // Administrator: [
        //   { title: "Realms", icon: "mdi-sitemap", link: "/realms" },
        //   { title: "Projects", icon: "mdi-presentation", link: "/projects" },
        //   { title: "Integrations", icon: "mdi-settings", link: "/integrations" }
        // ],
        // Configuration: [
        //   { title: "Menus", icon: "mdi-table-edit", link: "/realms" },
        //   { title: 'Preferences', icon: 'mdi-settings', link: '/config' }
        // ]
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

/* .logo { height: 90px; } */
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
.v-app-bar { color: #fff !important; box-shadow: none; }
.v-app-bar * { color: #fff !important; font-weight: bold; }

.v-application { font-family: "Nanum Gothic", sans-serif !important; }
</style>
