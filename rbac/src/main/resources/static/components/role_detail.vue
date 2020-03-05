<template>
  <v-row justify="center">
    <v-col sm="10">
      <v-btn small icon class="ml-n1 d-inline-flex blue-grey--text pb-1 text-none" to="/roles" >
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <h2 class="d-inline-flex">Roles > Project1_Admin</h2>
      <v-checkbox
          v-model="form.checkbox_mode"
          class="ml-2 d-inline-flex" dense flat hide-details dark
          label="Checkbox mode">
      </v-checkbox>
    </v-col>

    <v-col sm="10">
      <v-alert type="warning" dark dense outlined>
        This page is not implemented and JUST a Conceptual Design.
      </v-alert>
    </v-col>

    <v-col sm="10">
      <v-data-table
        class="blue-grey darken-3 elevation-1 wide"
        hide-default-footer
        show-expand
        :expanded.sync="expanded"
        :headers="headers"
        :items="items"
        item-key="name">

        <template v-slot:item.type="{ item }">
          <div class="d-flex align-center justify-space-around" v-if="item.type == 'role'">
            <v-select
                dense falt hide-details
                class="d-inline-flex caption ma-0"
                placeholder="Select role"
                :items="item.roles">
            </v-select>
            <v-select
                dense falt hide-details
                class="d-inline-flex caption ma-0"
                placeholder="Select system-role"
                :items="item.system_roles">
            </v-select>
          </div>
          <div v-else class="my-2">
            <span class="caption">
              N permissions are granted.
            </span>
            <span class="blue-grey--text caption">
              Expand to change permissions.
            </span>
          </div>
        </template>

        <template v-slot:expanded-item="{item, headers}">
          <td :colspan="headers.length">

          <template align="center" justify="space-around" v-if="item.type == 'permission'">
            <div v-for="p in item.permissions" :key="p.category" class="d-flex align-center">
              <div class="d-inline-flex col-sm-2">
                {{ p.category }}
              </div>

              <v-select v-if="!form.checkbox_mode" :items="p.items2" 
                  multiple dense chips small-chips
                  class="d-inline-flex caption"
                  placeholder="Select permissions"
                  >
              </v-select>
              <v-checkbox v-else v-for="i in p.items" :key="i.text" :label="i.name"
                  v-model="i.value"
                  dense hide-details
                  class="d-inline-flex col-sm-2">
              </v-checkbox>
            </div>
          </template>

          </td>
        </template>

        <!-- <template v-slot"item. -->
      </v-data-table>

      <v-progress-linear indeterminate v-if="loading"></v-progress-linear>
      <!-- <v-select :items="items[0].permissions[0].items" item-text="name"></v-select> -->
      <!-- <v-select :items="items2" item-text="text" item-value="value" return-object single-line multiple></v-select> -->
    </v-col>
  </v-row>
</template>

<script>
// https://github.com/FranckFreiburger/http-vue-loader/issues/34#issuecomment-375082104
module.exports = {
    data: function() {
      return {
        expanded: [],
        headers: [
          { text: 'Tool Name', value: 'name' }, 
          { text: 'Role or Permissions', value: 'type' }, 
          // { text: 'Actions', value: '' }, 
        ],
        // items2: [
        //   { text: 'Create', value: 'false' },
        //   { text: 'Delete', value: 'false' },
        //   { text: 'ManageDomains', value: 'false'},
        //   { text: 'Update', value: 'false'},
        //   { text: 'View', value: 'false'}
        // ],
        // items3: [
        //   'Create', 
        //   'Delete',
        //   'ManageDomains',
        //   'Update',
        //   'View',
        // ],
        items: [
          {
            name: 'Jenkins',
            type: 'permission',
            permissions: [
              {
                category: 'Credentials',
                items:[
                  {name: 'Create', value: false},
                  {name: 'Delete', value: false},
                  {name: 'ManageDomains', value: false},
                  {name: 'Update', value: false},
                  {name: 'View', value: false},
                ],
                items2: [ 'Create', 'Delete', 'ManageDomains', 'Update', 'View' ]
              },
              {
                category: 'Job',
                items: [
                  {name: 'Build', value: false},
                  {name: 'Cancel', value: false},
                  {name: 'Configure', value: false},
                  {name: 'Create', value: false},
                  {name: 'Delete', value: false},
                  {name: 'Discover', value: false},
                  {name: 'Move', value: false},
                  {name: 'Read', value: false},
                  {name: 'Workspace', value: false},
                ],
                items2: [ 'Build', 'Cancel', 'Configure', 'Create', 'Delete', 'Discover', 'Move', 'Read', 'Workspace' ]
              },
              {
                category: 'Run',
                items: [
                  {name: 'Delete', value: false},
                  {name: 'Replay', value: false},
                  {name: 'Update', value: false},
                ],
                items2: [ 'Delete', 'Replay', 'Update' ]
              },
              {
                category: 'View',
                items: [
                  {name: 'Configure', value: false},
                  {name: 'Create', value: false},
                  {name: 'Delete', value: false},
                  {name: 'Read', value: false},
                ],
                items2: [ 'Configure', 'Create', 'Delete', 'Read' ]
              },
              {
                category: 'SCM',
                items: [ {name: 'Tag', value: false} ],
                items2: [ 'Tag' ]
              },
            ]
          },
          {
            name: 'Kubernetes',
            type: 'permission',
            permissions: [
              { category: 'Pods',
                items:[
                  {name: 'Get', value: false}, {name: 'List', value: false}, {name: 'Create', value: false}, {name: 'Update', value: false},
                  {name: 'Patch', value: false}, {name: 'Watch', value: false}, {name: 'Delete', value: false}, {name: 'Deletecollection', value: false}
                ],
                items2: [ 'Get', 'List', 'Create', 'Update', 'Patch', 'Watch', 'Delete', 'Deletecollection' ] },
              { category: 'Deployments',
                items:[
                  {name: 'Get', value: false}, {name: 'List', value: false}, {name: 'Create', value: false}, {name: 'Update', value: false},
                  {name: 'Patch', value: false}, {name: 'Watch', value: false}, {name: 'Delete', value: false}, {name: 'Deletecollection', value: false}
                ],
                items2: [ 'Get', 'List', 'Create', 'Update', 'Patch', 'Watch', 'Delete', 'Deletecollection' ] },
              { category: 'StatefulSets',
                items:[
                  {name: 'Get', value: false}, {name: 'List', value: false}, {name: 'Create', value: false}, {name: 'Update', value: false},
                  {name: 'Patch', value: false}, {name: 'Watch', value: false}, {name: 'Delete', value: false}, {name: 'Deletecollection', value: false}
                ],
                items2: [ 'Get', 'List', 'Create', 'Update', 'Patch', 'Watch', 'Delete', 'Deletecollection' ] },
              { category: 'DaemonSets',
                items:[
                  {name: 'Get', value: false}, {name: 'List', value: false}, {name: 'Create', value: false}, {name: 'Update', value: false},
                  {name: 'Patch', value: false}, {name: 'Watch', value: false}, {name: 'Delete', value: false}, {name: 'Deletecollection', value: false}
                ],
                items2: [ 'Get', 'List', 'Create', 'Update', 'Patch', 'Watch', 'Delete', 'Deletecollection' ] },
              { category: 'Services',
                items:[
                  {name: 'Get', value: false}, {name: 'List', value: false}, {name: 'Create', value: false}, {name: 'Update', value: false},
                  {name: 'Patch', value: false}, {name: 'Watch', value: false}, {name: 'Delete', value: false}, {name: 'Deletecollection', value: false}
                ],
                items2: [ 'Get', 'List', 'Create', 'Update', 'Patch', 'Watch', 'Delete', 'Deletecollection' ] },
              { category: 'Ingresses',
                items:[
                  {name: 'Get', value: false}, {name: 'List', value: false}, {name: 'Create', value: false}, {name: 'Update', value: false},
                  {name: 'Patch', value: false}, {name: 'Watch', value: false}, {name: 'Delete', value: false}, {name: 'Deletecollection', value: false}
                ],
                items2: [ 'Get', 'List', 'Create', 'Update', 'Patch', 'Watch', 'Delete', 'Deletecollection' ] },
            ]
          },
          {
            name: 'Grafana',
            type: 'role',
            roles: ['Admin', 'Editor', 'Viewer'],
            system_roles: ['Grafana Admin']
          },
          {
            name: 'Grafana-2',
            type: 'role',
            roles: ['Admin', 'Editor', 'Viewer'],
            system_roles: ['Grafana Admin']
          },
          {
            name: 'Harbor',
            type: 'role',
            roles: ['ProjectAdmin', 'Master', 'Developer', 'Guest', 'Limited Guest'],
            system_roles: ['Harbor system administrator', 'Anonymous']
          },
        ],
        loading: false,
        form: {
          checkbox_mode: false
        }
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