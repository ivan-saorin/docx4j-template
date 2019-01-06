<section xml:id="h1-heading-8-">
  <title>h1 Heading 8-)</title>
  <section xml:id="h2-heading">
    <title>h2 Heading</title>
    <section xml:id="h3-heading">
      <title>h3 Heading</title>
      <section xml:id="h4-heading">
        <title>h4 Heading</title>
        <section xml:id="h5-heading">
          <title>h5 Heading</title>
          <simplesect xml:id="h6-heading">
            <title>h6 Heading</title>
            <para>
            </para>
          </simplesect>
        </section>
      </section>
    </section>
  </section>
<note><title>Upcoming changes</title>
    <para>Future versions of this feature may not be backward compatible. Consider implementing the revised interface now.
  </para>
</note>  <section xml:id="horizontal-rules">
    <title>Horizontal Rules</title>
  </section>
  <section xml:id="typographic-replacements">
    <title>Typographic replacements</title>
    <para>
      Enable typographer option to see result.
    </para>
    <orderedlist numeration="loweralpha">
      <listitem override="3">
        <orderedlist numeration="upperalpha">
          <listitem override="3">
            <orderedlist numeration="loweralpha">
              <listitem override="18">
                <orderedlist numeration="upperalpha" spacing="compact">
                  <listitem override="18">
                    <para>
                      (tm) (TM) (p) (P) +-
                    </para>
                  </listitem>
                </orderedlist>
              </listitem>
            </orderedlist>
          </listitem>
        </orderedlist>
      </listitem>
    </orderedlist>
    <para>
      test.. test… test….. test?….. test!….
    </para>
    <para>
      !!!!!! ???? ,, – —
    </para>
    <para>
      <quote>Smartypants, double quotes</quote> and <quote>single
      quotes</quote>
    </para>
  </section>
  <section xml:id="emphasis">
    <title>Emphasis</title>
    <para>
      <emphasis role="strong">This is bold text</emphasis>
    </para>
    <para>
      <emphasis role="strong">This is bold text</emphasis>
    </para>
    <para>
      <emphasis>This is italic text</emphasis>
    </para>
    <para>
      <emphasis>This is italic text</emphasis>
    </para>
    <para>
      <emphasis role="strikethrough">Strikethrough</emphasis>
    </para>
  </section>
  <section xml:id="blockquotes">
    <title>Blockquotes</title>
    <blockquote>
      <para>
        Blockquotes can also be nested… &gt; …by using additional
        greater-than signs right next to each other… &gt; &gt; …or with
        spaces between arrows.
      </para>
    </blockquote>
  </section>
  <section xml:id="lists">
    <title>Lists</title>
    <para>
      Unordered
    </para>
    <itemizedlist spacing="compact">
      <listitem>
        <para>
          Create a list by starting a line with <literal>+</literal>,
          <literal>-</literal>, or <literal>*</literal>
        </para>
      </listitem>
      <listitem>
        <para>
          Sub-lists are made by indenting 2 spaces:
        </para>
        <itemizedlist spacing="compact">
          <listitem>
            <para>
              Marker character change forces new list start:
            </para>
            <itemizedlist spacing="compact">
              <listitem>
                <para>
                  Ac tristique libero volutpat at
                </para>
              </listitem>
              <listitem>
                <para>
                  Facilisis in pretium nisl aliquet
                </para>
              </listitem>
              <listitem>
                <para>
                  Nulla volutpat aliquam velit
                </para>
              </listitem>
            </itemizedlist>
          </listitem>
        </itemizedlist>
      </listitem>
      <listitem>
        <para>
          Very easy!
        </para>
      </listitem>
    </itemizedlist>
    <para>
      Ordered
    </para>
    <orderedlist numeration="arabic">
      <listitem>
        <para>
          Lorem ipsum dolor sit amet
        </para>
      </listitem>
      <listitem>
        <para>
          Consectetur adipiscing elit
        </para>
      </listitem>
      <listitem>
        <para>
          Integer molestie lorem at massa
        </para>
      </listitem>
      <listitem>
        <para>
          You can use sequential numbers…
        </para>
      </listitem>
      <listitem>
        <para>
          …or keep all the numbers as <literal>1.</literal>
        </para>
      </listitem>
    </orderedlist>
    <para>
      Start numbering with offset:
    </para>
    <orderedlist numeration="arabic" spacing="compact">
      <listitem override="57">
        <para>
          foo
        </para>
      </listitem>
      <listitem>
        <para>
          bar
        </para>
      </listitem>
    </orderedlist>
  </section>
  <section xml:id="code">
    <title>Code</title>
    <para>
      Inline <literal>code</literal>
    </para>
    <para>
      Indented code
    </para>
    <programlisting>
// Some comments
line 1 of code
line 2 of code
line 3 of code
</programlisting>
    <para>
      Block code <quote>fences</quote>
    </para>
    <programlisting>
Sample text here...
</programlisting>
    <para>
      Syntax highlighting
    </para>
    <programlisting language="javascript">
var foo = function (bar) {
  return bar++;
};

console.log(foo(5));
</programlisting>
  </section>
  <section xml:id="tables">
    <title>Tables</title>
    <informaltable>
      <tgroup cols="2">
        <colspec colwidth="35*" align="left" />
        <colspec colwidth="64*" align="left" />
        <thead>
          <row>
            <entry>
              Option
            </entry>
            <entry>
              Description
            </entry>
          </row>
        </thead>
        <tbody>
          <row>
            <entry>
              data
            </entry>
            <entry>
              path to data files to supply the data that will be passed
              into templates.
            </entry>
          </row>
          <row>
            <entry>
              engine
            </entry>
            <entry>
              engine to be used for processing templates. Handlebars is
              the default.
            </entry>
          </row>
          <row>
            <entry>
              ext
            </entry>
            <entry>
              extension to be used for dest files.
            </entry>
          </row>
        </tbody>
      </tgroup>
    </informaltable>
    <para>
      Right aligned columns
    </para>
    <informaltable>
      <tgroup cols="2">
        <colspec colwidth="36*" align="right" />
        <colspec colwidth="63*" align="right" />
        <thead>
          <row>
            <entry>
              Option
            </entry>
            <entry>
              Description
            </entry>
          </row>
        </thead>
        <tbody>
          <row>
            <entry>
              data
            </entry>
            <entry>
              path to data files to supply the data that will be passed
              into templates.
            </entry>
          </row>
          <row>
            <entry>
              engine
            </entry>
            <entry>
              engine to be used for processing templates. Handlebars is
              the default.
            </entry>
          </row>
          <row>
            <entry>
              ext
            </entry>
            <entry>
              extension to be used for dest files.
            </entry>
          </row>
        </tbody>
      </tgroup>
    </informaltable>
  </section>
  <section xml:id="links">
    <title>Links</title>
    <para>
      <link xlink:href="http://dev.nodeca.com">link text</link>
    </para>
    <para>
      <link xlink:href="http://nodeca.github.io/pica/demo/">link with
      title</link>
    </para>
    <para>
      Autoconverted link https://github.com/nodeca/pica (enable linkify
      to see)
    </para>
  </section>
  <section xml:id="images">
    <title>Images</title>
    <para>
      <inlinemediaobject>
        <imageobject>
          <imagedata fileref="https://octodex.github.com/images/minion.png" />
        </imageobject>
      </inlinemediaobject> <inlinemediaobject>
        <imageobject>
          <objectinfo>
            <title>
              The Stormtroopocat
            </title>
          </objectinfo>
          <imagedata fileref="https://octodex.github.com/images/stormtroopocat.jpg" />
        </imageobject>
      </inlinemediaobject>
    </para>
    <para>
      Like links, Images also have a footnote style syntax
    </para>
    <figure>
      <title>Alt text</title>
      <mediaobject>
        <imageobject>
          <imagedata fileref="https://octodex.github.com/images/dojocat.jpg" />
        </imageobject>
        <textobject><phrase>Alt text</phrase></textobject>
      </mediaobject>
    </figure>
    <para>
      With a reference later in the document defining the URL location:
    </para>
  </section>
  <section xml:id="plugins">
    <title>Plugins</title>
    <para>
      The killer feature of <literal>markdown-it</literal> is very
      effective support of
      <link xlink:href="https://www.npmjs.org/browse/keyword/markdown-it-plugin">syntax
      plugins</link>.
    </para>
    <section xml:id="emojies">
      <title><link xlink:href="https://github.com/markdown-it/markdown-it-emoji">Emojies</link></title>
      <blockquote>
        <para>
          Classic markup: :wink: :crush: :cry: :tear: :laughing: :yum:
        </para>
        <para>
          Shortcuts (emoticons): :-) :-( 8-) ;)
        </para>
      </blockquote>
      <para>
        see
        <link xlink:href="https://github.com/markdown-it/markdown-it-emoji#change-output">how
        to change output</link> with twemoji.
      </para>
    </section>
    <section xml:id="subscript-superscript">
      <title><link xlink:href="https://github.com/markdown-it/markdown-it-sub">Subscript</link>
      /
      <link xlink:href="https://github.com/markdown-it/markdown-it-sup">Superscript</link></title>
      <itemizedlist spacing="compact">
        <listitem>
          <para>
            19<superscript>th</superscript>
          </para>
        </listitem>
        <listitem>
          <para>
            H<subscript>2</subscript>O
          </para>
        </listitem>
      </itemizedlist>
    </section>
    <section xml:id="ins">
      <title><link xlink:href="https://github.com/markdown-it/markdown-it-ins">&lt;ins&gt;</link></title>
      <para>
        ++Inserted text++
      </para>
    </section>
    <section xml:id="mark">
      <title><link xlink:href="https://github.com/markdown-it/markdown-it-mark">&lt;mark&gt;</link></title>
      <para>
        ==Marked text==
      </para>
    </section>
    <section xml:id="footnotes">
      <title><link xlink:href="https://github.com/markdown-it/markdown-it-footnote">Footnotes</link></title>
      <para>
        Footnote 1 link<footnote>
          <para>
            Footnote <emphasis role="strong">can have markup</emphasis>
          </para>
          <para>
            and multiple paragraphs.
          </para>
        </footnote>.
      </para>
      <para>
        Footnote 2 link<footnote>
          <para>
            Footnote text.
          </para>
        </footnote>.
      </para>
      <para>
        Inline footnote<footnote>
          <para>
            Text of inline footnote
          </para>
        </footnote> definition.
      </para>
      <para>
        Duplicated footnote reference<footnote>
          <para>
            Footnote text.
          </para>
        </footnote>.
      </para>
    </section>
    <section xml:id="definition-lists">
      <title><link xlink:href="https://github.com/markdown-it/markdown-it-deflist">Definition
      lists</link></title>
      <variablelist>
        <varlistentry>
          <term>
            Term 1
          </term>
          <listitem>
            <para>
              Definition 1 with lazy continuation.
            </para>
          </listitem>
        </varlistentry>
        <varlistentry>
          <term>
            Term 2 with <emphasis>inline markup</emphasis>
          </term>
          <listitem>
            <para>
              Definition 2
            </para>
            <programlisting>
{ some code, part of Definition 2 }
</programlisting>
            <para>
              Third paragraph of definition 2.
            </para>
          </listitem>
        </varlistentry>
      </variablelist>
      <para>
        <emphasis>Compact style:</emphasis>
      </para>
      <variablelist spacing="compact">
        <varlistentry>
          <term>
            Term 1
          </term>
          <listitem>
            <para>
              Definition 1
            </para>
          </listitem>
        </varlistentry>
        <varlistentry>
          <term>
            Term 2
          </term>
          <listitem>
            <para>
              Definition 2a
            </para>
            <para>
              Definition 2b
            </para>
          </listitem>
        </varlistentry>
      </variablelist>
    </section>
    <section xml:id="abbreviations">
      <title><link xlink:href="https://github.com/markdown-it/markdown-it-abbr">Abbreviations</link></title>
      <para>
        This is HTML abbreviation example.
      </para>
      <para>
        It converts <quote>HTML</quote>, but keep intact partial entries
        like <quote>xxxHTMLyyy</quote> and so on.
      </para>
      <para>
        *[HTML]: Hyper Text Markup Language
      </para>
    </section>
    <section xml:id="custom-containers">
      <title><link xlink:href="https://github.com/markdown-it/markdown-it-container">Custom
      containers</link></title>
      <para>
        <emphasis>here be dragons</emphasis>
      </para>
    </section>
  </section>
</section>
