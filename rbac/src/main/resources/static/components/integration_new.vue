<template>
  <v-row justify="start">
    <v-col sm="10" offset-sm="1">
      <v-btn small icon class="ml-n2 d-inline-flex blue-grey--text pb-1 text-none" to="/integrations" >
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <h2 class="d-inline-flex">New Integration</h2>
    </v-col>

    <v-col sm="5" offset-sm="1">
      <v-select label="Kind *" :items="fields.kind"></v-select>
      <v-text-field label="Name *" counter="30"> </v-text-field>
      <v-text-field label="Endpoint *"
          persistent-hint
          hint="https://oidc.tool.com">
      </v-text-field>

        <v-data-table
          :headers="table.headers"
          :items="table.items"
          hide-default-footer
          class="blue-grey darken-3 mt-10 elevation-1">
          <template v-slot:top>
            <div class="px-4 pt-3 d-flex justify-space-between">
              <h3 class="d-inline-flex">Attributes</h3>

              <v-btn text class="d-inline-flex blue-grey--text text-none" @click="table.items.push({key:'', val:''})">
                <v-icon>mdi-plus</v-icon>
                <span class="body-2 font-weight-bold">Add</span>
              </v-btn>
            </div>
          </template>

          <template v-slot:item.key="{item}">
            <v-edit-dialog>
              {{ item.key }}
              <span v-if="!item.key" class="blue-grey--text">Click to edit</span>
              <template v-slot:input>
                <v-text-field v-model="item.key" class="mb-3" persistent-hit hint="Type and Press enter to save" single-line></v-text-field>
              </template>
            </v-edit-dialog>
          </template>

          <template v-slot:item.val="{item}">
            <v-edit-dialog>
              {{ item.val }}
              <span v-if="!item.val" class="blue-grey--text">Click to edit</span>
              <template v-slot:input>
                <v-text-field v-model="item.val" class="mb-3" persistent-hit hint="Type and Press enter to save" single-line></v-text-field>
              </template>
            </v-edit-dialog>
          </template>

        </v-data-table>
    </v-col>

  </v-row>
</template>

<script>
// https://github.com/FranckFreiburger/http-vue-loader/issues/34#issuecomment-375082104
module.exports = {
    data: function() {
      return {
        table: {
          headers: [
            {text: 'Key', value: 'key'},
            {text: 'Value', value: 'val'},
          ],
          items: [
            {key: '', val: 'val'},
          ]
        },
        items: [],
        fields: {
          kind: ['Grafana', 'Jenkins', 'Habor', 'Azure AD']
        },
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
        var vm = this
        var success = function(res){ vm.items = res.data.tools; vm.loading = false; }
        var url = this.endpoints.realm

        this.loading = true
        axios.get(url).then(success)
      }
    }
  }
</script>