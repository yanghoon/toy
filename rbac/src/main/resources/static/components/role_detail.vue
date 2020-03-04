<template>
  <v-row justify="center">
    <v-col sm="10">
      <v-btn small icon class="ml-n1 d-inline-flex blue-grey--text pb-1 text-none" to="/roles" >
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <h2 class="d-inline-flex">Roles > Project1_Admin</h2>
    </v-col>

    <v-col sm="10">
      <v-data-table
        class="blue-grey darken-3 elevation-1"
        hide-default-footer
        show-expand
        :headers="headers"
        :items="items">

        <template v-slot:item.roles="{ item }">
          <v-row align="center" justify="space-around" v-if="item.roles">
            <v-col>
              <v-select dense falt hide-details class="caption" placeholder="Role" :items="item.roles"></v-select>
            </v-col>
            <v-col>
              <v-select dense falt hide-details class="caption" placeholder="System Role" :items="item.system_roles"></v-select>
            </v-col>
          </v-row>

          <v-row align="center" justify="space-around" v-if="item.permissions">
            {{ item.permissions }}
          </v-row>
        </template>
      </v-data-table>

      <v-progress-linear indeterminate v-if="loading"></v-progress-linear>
    </v-col>
  </v-row>
</template>

<script>
// https://github.com/FranckFreiburger/http-vue-loader/issues/34#issuecomment-375082104
module.exports = {
    data: function() {
      return {
        headers: [
          { text: 'Tool Name', value: 'name' }, 
          { text: 'Role of Permissions', value: 'roles' }, 
          { text: 'Actions', value: '' }, 
        ],
        items: [
          {
            name: 'Jenkins',
            permissions: [
              
            ]
          },
          {
            name: 'Kubernetes',
          },
          {
            name: 'Grafana',
            roles: ['Admin', 'Editor', 'Viewer'],
            system_roles: ['Grafana Admin']
          },
          {
            name: 'Grafana-2',
            roles: ['Admin', 'Editor', 'Viewer'],
            system_roles: ['Grafana Admin']
          },
          {
            name: 'Harbor',
            roles: ['ProjectAdmin', 'Master', 'Developer', 'Guest', 'Limited Guest'],
            system_roles: ['Harbor system administrator', 'Anonymous']
          },
        ],
        loading: false,
        form: {}
      }
    },
    computed: {
      'endpoints': function(){
        // https://github.com/vuejs/vue/issues/1964#issuecomment-162210972
        var r = global.realm || {}
        return {
          realm: '/companies/' + r.name + '/integrations',
          // projects_new: '/companies/' + r.name + '/projects/new',
          invalid: !r.name
        }
      }
    },
    watch: {
      'endpoints.realm': function(url){
        if(!this.endpoints.invalid)
          this.getItems()
      }
    },
    methods: {
      'getItems': function(){
        // var vm = this
        // var success = function(res){ vm.items = res.data.tools; vm.loading = false; }
        // var url = this.endpoints.realm

        // this.loading = true
        // axios.get(url).then(success)
      }
    }
  }
</script>