(ns hello.core)

(defn ^:export entrypoint [request response]
  (.send response "Hello, world!"))
