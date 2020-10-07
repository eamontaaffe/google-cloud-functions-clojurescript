(require '[lumo.build.api :refer [build]])

(build "src"
  {:output-to "index.js"
   :optimizations :advanced
   :target :nodejs})
