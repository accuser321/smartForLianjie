import directive from './directives'

const importDirective = Vue => {
  /*
   * */
  Vue.directive('throttle', directive.throttle)
}

export default importDirective
