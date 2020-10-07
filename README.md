# Google Cloud Functions with ClojureScript

ClojureScript running on Google Cloud Functions as quickly as possible.

We are going to use the NodeJS runtime along with the awesome [Lumo](http://lumo-cljs.org) ClojureScript compiler. The Lumo compiler is a completely NodeJS based clojurescript compiler that does not require the JVM to run. This means we can use it from within our NodeJS project without installing any external dependencies.

## Step one

Create a `package.json` file.

```json
{
  "main": "index.js",
  "scripts": {
    "gcp-build": "lumo -c src build.cljs"
  },
  "devDependencies": {
    "lumo-cljs": "^1.10.1"
  }
}
```

Notice the `gcp-build` script. This is triggered automatically when we deploy our Cloud Function. Google knows to look for this command and run it after installing the required packages.

## Step two

Write our `build.cljs` file.

```clojure
(require '[lumo.build.api :refer [build]])

(build "src"
  {:output-to "index.js"
   :optimizations :advanced
   :target :nodejs})
```

This file tells Lumo how to build our project. It itself is just a ClojureScript file. When it is executed, it runs the build command which converts our ClojureScript files into NodeJS files.

## Step three

Write some code.

```clojure
;; src/hello/core.clj

(ns hello.core)

(defn ^:export entrypoint [request response]
  (.send response "Hello, world!"))
```

About as simple as it gets. For every request, responde with, "Hello, world!". Cloud Functions uses ExpressJS as a framework, so the request and response objects conform to the ExpressJS specification.

## Step four

Deploy it.

```sh
gcloud functions deploy hello \
  --runtime nodejs12 \
  --entry-point hello.core.entrypoint \
  --trigger-http \
  --allow-unauthenticated
```

There you have it, a ClojureScript Cloud Function as quickly as possible. We just deployed a function which uses the NodeJS runtime, that can be triggered by HTTP and doesn't need any authentication.

## Conclusion

```sh
curl https://<REGION>-<PROJECT>.cloudfunctions.net/<FUNCTION>
> Hello, world!
```

Pretty easy right?
