(defproject heard-beat "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[bidi "2.0.16"]
                 [com.cemerick/piggieback "0.2.1"]
                 [com.taoensso/timbre "4.8.0"]
                 [hiccups "0.3.0"]
                 [macchiato/core "0.1.6"]
                 [macchiato/env "0.0.5"]
                 [mount "0.1.11"]
                 [org.clojure/core.async "0.3.442"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.473"]]
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-doo "0.1.7"]
            [macchiato/lein-npm "0.6.3"]
            ;; FOR_CODER
            [cider/cider-nrepl "0.15.0-SNAPSHOT"]
            [refactor-nrepl "2.3.0-SNAPSHOT"]
            ;; end FOR_CIDER
            [lein-figwheel "0.5.9"]
            [lein-cljsbuild "1.1.4"]]
  :npm {:dependencies [[source-map-support "0.4.6"]
                       [web3 "0.18.4"]
                       [truffle-contract "2.0.0"]]
        :write-package-json true}
  :source-paths ["src" "target/classes"]
  :clean-targets ["target"]
  :target-path "target"
  :profiles
  {:dev
   {:npm {:package {:main "target/out/heard-beat.js"
                    :scripts {:start "node target/out/heard-beat.js"}}}
    :cljsbuild
    {:builds {:dev
              {:source-paths ["env/dev" "src"]
               :figwheel     true
               :compiler     {:main          heard-beat.app
                              :output-to     "target/out/heard-beat.js"
                              :output-dir    "target/out"
                              :target        :nodejs
                              :optimizations :none
                              :pretty-print  true
                              :source-map    true
                              :source-map-timestamp false}}}}
    :figwheel
    {:http-server-root "public"
     :nrepl-port 7000
     :nrepl-host "0.0.0.0"
     :repl false
     :reload-clj-files {:clj false :cljc true}
     :nrepl-middleware [
                        ;; FOR_CIDER
                        cider.nrepl/cider-middleware
                        refactor-nrepl.middleware/wrap-refactor
                        ;; end FOR_CIDER
                        cemerick.piggieback/wrap-cljs-repl
                        ]}
    :source-paths ["env/dev"]
    :repl-options {:init-ns user}}
   :test
   {:cljsbuild
    {:builds
     {:test
      {:source-paths ["env/test" "src" "test"]
       :compiler     {:main heard-beat.app
                      :output-to     "target/test/heard-beat.js"
                      :target        :nodejs
                      :optimizations :none
                      :pretty-print  true
                      :source-map    true}}}}
    :doo {:build "test"}}
   :release
   {:npm {:package {:main "target/release/heard-beat.js"
                    :scripts {:start "node target/release/heard-beat.js"}}}
    :cljsbuild
    {:builds
     {:release
      {:source-paths ["env/prod" "src"]
       :compiler     {:main          heard-beat.app
                      :output-to     "target/release/heard-beat.js"
                      :target        :nodejs
                      :optimizations :simple
                      :pretty-print  false}}}}}}
  :aliases
  {"build" ["do"
            ["clean"]
            ["npm" "install"]
            ["figwheel" "dev"]]
   "package" ["do"
              ["clean"]
              ["npm" "install"]
              ["with-profile" "release" "npm" "init" "-y"]
              ["with-profile" "release" "cljsbuild" "once"]]
   "test" ["do"
           ["npm" "install"]
           ["with-profile" "test" "doo" "node"]]})
