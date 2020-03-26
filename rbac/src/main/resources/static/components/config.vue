<template>
  <v-row justify="start">
    <v-col sm="10" offset-sm="1">
      <h2 class="d-inline-flex">Preferences</h2>

      <v-btn text class="ml-1 d-inline-flex blue-grey--text text-none" @click="updateItem">
        <v-icon left>mdi-content-save</v-icon>
        Save
      </v-btn>
      <!-- {{ JSON.stringify(config, null, 2) }} -->

      <!-- <v-form v-if="config.links" class="pt-8">
        <h3>Links</h3>
        <template v-for="(link, name) in config.links">
          <v-text-field :label="name" :key="name" :value="link" disabled></v-text-field>
        </template>
      </v-form>

      <v-form v-if="config.authorization" class="pt-8">
        <h3>Authorization Server</h3>
        <template v-for="(prop, name) in config.authorization">

          <v-text-field :label="name" :key="name" :value="prop" disabled
            v-if="typeof prop !== 'object'">
          </v-text-field>

          <template v-if="typeof prop == 'object'">
            <h4 :key="name" class="text-capitalize">{{ name }}</h4>
            <v-text-field v-for="(v, k) in prop" :label="k" :key="k" :value="v" disabled></template>
          </template>

        </template>
      </v-form>

      <v-progress-linear indeterminate v-if="loading"></v-progress-linear> -->

    </v-col>

    <v-col sm="5" offset-sm="1">
      <v-select label="Kind *" :items="[config.auth.kind]" v-model="config.auth.kind" disabled></v-select>
      <v-text-field label="Name *" counter="30" v-model="config.auth.name" disabled></v-text-field>
      <v-text-field label="Endpoint *"
          persistent-hint
          hint="https://oidc.tool.com"
          v-model="config.auth.endpoint">
      </v-text-field>

        <v-data-table
          :headers="table.headers"
          :items="auth_attr"
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

          <template v-slot:item.value="{item}">
            <v-edit-dialog>
              {{ item.value }}
              <span v-if="!item.value" class="blue-grey--text">Click to edit</span>
              <template v-slot:input>
                <v-text-field v-model="item.value" class="mb-3" persistent-hit hint="Type and Press enter to save" single-line></v-text-field>
              </template>
            </v-edit-dialog>
          </template>

        </v-data-table>
    </v-col>

  </v-row>
</template>

<script>
// https://hoorooroob.tistory.com/entry/React-React-Naive-TIPS-axios-%EC%99%80-fetch-%EC%96%B4%EB%96%A4-%EA%B2%83%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%A0%EA%B9%8C
// https://ux.stories.pe.kr/138
module.exports = {
    data: function() {
      return {
        table: {
          headers: [
            {text: 'Key', value: 'key'},
            {text: 'Value', value: 'value'},
          ],
          items: []
        },
        endpoints: {
          config: '/config'
        },
        loading: false,
        config: {
          auth: {}
        }
      }
    },
    computed: {
      'auth_attr': function(){
        return this.config.auth ? this.expand(this.config.auth.attributes) : []
      }
    },
    beforeMount: function() {
      this.getItem()
    },
    methods: {
      'getItem': function(){
        var vm = this
        var success = function(res){ vm.config = res.data; vm.loading = false; }
        var url = this.endpoints.config

        this.loading = true
        axios.get(url).then(success)
      },
      'updateItem': function(){
        var url = this.endpoints.config

        this.config.auth.attributes = this.auth_attr.reduce( (acc, val) => { acc[val.key] = val.value; return acc }, {})
        console.log(this.config)

        this.loading = true
        axios.put(url, this.config).then(this.getItem)
      },
      'expand': function(obj) {
        return obj ? Object.entries(obj).map(([key, value]) => ({key,value})) : [];
      }
    }
  }
</script>